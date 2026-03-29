package ma.youcode.regalis.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.quiz.QuizCreateDTO;
import ma.youcode.regalis.dto.quiz.QuizResponseDTO;
import ma.youcode.regalis.dto.quiz.QuizUpdateDTO;
import ma.youcode.regalis.entity.Quiz;
import ma.youcode.regalis.entity.Module;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.QuizMapper;
import ma.youcode.regalis.repository.QuizRepository;
import ma.youcode.regalis.repository.ModuleRepository;
import ma.youcode.regalis.service.QuizService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final ModuleRepository moduleRepository;
    private final QuizMapper quizMapper;

    @Override
    public QuizResponseDTO createQuiz(QuizCreateDTO dto) {
        Module module = moduleRepository.findById(dto.moduleId())
                .orElseThrow(() -> new EntityNotFoundException("Module not found with id: " + dto.moduleId()));

        Quiz quiz = quizMapper.toEntity(dto);
        quiz.setModule(module);
        Quiz savedQuiz = quizRepository.save(quiz);
        return quizMapper.toDTO(savedQuiz);
    }

    @Override
    @Transactional(readOnly = true)
    public QuizResponseDTO getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with id: " + id));
        return quizMapper.toDTO(quiz);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizResponseDTO> getQuizzesByModuleId(Long moduleId) {
        return quizRepository.findByModuleId(moduleId).stream()
                .map(quizMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizResponseDTO> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(quizMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuizResponseDTO> getPagedQuizzes(String search, Long moduleId, Pageable pageable) {
        return quizRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.isBlank()) {
                String pattern = "%" + search.toLowerCase() + "%";
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(root.get("content")), pattern)
                ));
            }

            if (moduleId != null) {
                predicates.add(cb.equal(root.get("module").get("id"), moduleId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable).map(quizMapper::toDTO);
    }

    @Override
    public QuizResponseDTO updateQuiz(Long id, QuizUpdateDTO dto) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with id: " + id));

        if (dto.moduleId() != null && !dto.moduleId().equals(quiz.getModule().getId())) {
            Module newModule = moduleRepository.findById(dto.moduleId())
                    .orElseThrow(() -> new EntityNotFoundException("Module not found with id: " + dto.moduleId()));
            quiz.setModule(newModule);
        }

        quizMapper.updateEntityFromDTO(dto, quiz);
        Quiz updatedQuiz = quizRepository.save(quiz);
        return quizMapper.toDTO(updatedQuiz);
    }

    @Override
    public void deleteQuiz(Long id) {
        if (!quizRepository.existsById(id)) {
            throw new EntityNotFoundException("Quiz not found with id: " + id);
        }
        quizRepository.deleteById(id);
    }
}
