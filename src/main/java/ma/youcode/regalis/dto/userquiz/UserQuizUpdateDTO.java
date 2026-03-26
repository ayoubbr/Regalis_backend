package ma.youcode.regalis.dto.userquiz;

import java.util.List;

public record UserQuizUpdateDTO(
        Boolean completed,
        List<UserAnswerDTO> answers) {

    public record UserAnswerDTO(
            Long questionId,
            String selectedOptionId) {
    }
}
