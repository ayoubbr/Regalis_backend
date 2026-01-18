# ♚ REGALIS - Plateforme d'Apprentissage des Échecs Gamifiée

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Angular](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)

## 📖 Présentation Générale

**Regalis** est une plateforme web interactive dédiée à l'apprentissage des échecs. Elle repose sur des mécaniques de gamification avancées (points d'expérience, niveaux, défis quotidiens, système de "streak") pour offrir une expérience motivante et progressive.

L'objectif est d'aider les utilisateurs à développer leurs compétences tactiques et stratégiques de manière ludique.

### 🛠️ Stack Technique

*   **Backend** : Spring Boot (Java)
*   **Base de données** : MySQL
*   **Frontend** : Angular (Framework TypeScript)

---

## 🎯 Objectifs Fonctionnels

### 2.1. Gestion des Utilisateurs
*   **Authentification** : Inscription et connexion sécurisées via Spring Security (JWT).
*   **Profil Utilisateur** : Suivi des statistiques personnelles :
    *   `totalXp` (Points d'expérience)
    *   `level` (Niveau actuel)
    *   `currentStreak` (Série de jours consécutifs actifs)
    *   Statistiques d'apprentissage détaillées.

### 2.2. Système de Progression
*   **Calcul d'XP** : Gain automatique après chaque activité (leçons, puzzles, défis).
*   **Niveaux** : Montée en niveau automatique gérée par le backend.
*   **Streak (Série)** :
    *   Incrémentation lors d'une activité quotidienne.
    *   Réinitialisation en cas d'inactivité.

### 2.3. Contenus Pédagogiques
Les modules sont classés par thèmes (Bases, Tactiques, Ouvertures, Finales, Stratégie). Chaque module comprend :
*   Leçons interactives
*   Quiz
*   Puzzles d'échecs
*   Tests de validation

### 2.4. Gamification & Défis
*   **Défis Quotidiens** : Un puzzle unique généré chaque jour par `Spring Scheduler`. Réussir ce défi octroie un bonus d'XP.
*   **Achievements (Succès)** : Déblocage de badge/succès lorsque des conditions spécifiques sont remplies (ex: "7 jours de suite").

### 2.5. Système de Puzzles
Le cœur de l'entraînement tactique :
*   Utilisation du format **FEN** pour les positions.
*   Validation des coups (Légal/Illégal) et comparaison avec la solution optimale.
*   Attribution d'XP en fonction de la difficulté et du nombre d'essais.

### 2.6. Classements (Leaderboard)
Classement dynamique des joueurs basé sur l'XP (Hebdomadaire, Mensuel, Global).

---

## 🏗️ Architecture & Conception

### Backend (Spring Boot)
*   **Architecture en couches** : Controller, Service, Repository.
*   **Sécurité** : Spring Security & JWT.
*   **Planification** : Spring Scheduler pour les tâches récurrentes (Défis, Reset classements).

### Frontend (Angular)
*   Structure modulaire (Modules, Components, Services).
*   Dashboard utilisateur riche (Charts, Statuts).
*   Interface d'administration pour la gestion de contenu.

---

## 💾 Modèle de Données (Entities)

Voici la structure des principales entités du projet.

### `User`
| Attribute | Type | Description |
| :--- | :--- | :--- |
| `id` | `Long` | Primary Key |
| `username` | `String` | Unique username |
| `email` | `String` | Unique email |
| `password` | `String` | Encrypted password |
| `role` | `Role` | `USER` or `ADMIN` |
| `totalXp` | `Integer` | Total experience points |
| `level` | `Integer` | Current user level |
| `currentStreak` | `Integer` | Consecutive active days |
| `lastActiveDate` | `LocalDate` | Last login/activity date |
| `createdAt` | `LocalDateTime` | Account creation timestamp |

### `Module`
| Attribute | Type | Description |
| :--- | :--- | :--- |
| `id` | `Long` | Primary Key |
| `name` | `String` | e.g., "Tactics", "Openings" |
| `description` | `String` | Module description |
| `orderIndex` | `Integer` | Display order |

### `Lesson`
| Attribute | Type | Description |
| :--- | :--- | :--- |
| `id` | `Long` | Primary Key |
| `title` | `String` | Lesson title |
| `content` | `Text` | Markdown or HTML content |
| `difficulty` | `Integer` | Difficulty rating |
| `xpReward` | `Integer` | XP gained on completion |
| `module_id` | `Long` | Foreign Key -> Module |

### `Puzzle`
| Attribute | Type | Description |
| :--- | :--- | :--- |
| `id` | `Long` | Primary Key |
| `fenPosition` | `String` | FEN string of the board position |
| `solutionMoves` | `String` | Correct move sequence (SAN or coordinate) |
| `difficulty` | `Integer` | Difficulty rating |
| `xpReward` | `Integer` | XP detailed |
| `module_id` | `Long` | Foreign Key -> Module |

### `UserProgress`
| Attribute | Type | Description |
| :--- | :--- | :--- |
| `id` | `Long` | Primary Key |
| `user_id` | `Long` | FK -> User |
| `lesson_id` | `Long` | FK -> Lesson |
| `completed` | `Boolean` | Completion status |
| `completionDate` | `LocalDateTime` | When it was finished |

### `DailyChallenge`
| Attribute | Type | Description |
| :--- | :--- | :--- |
| `id` | `Long` | Primary Key |
| `challengeDate` | `LocalDate` | Date of the challenge |
| `puzzle_id` | `Long` | The puzzle for the day |
| `completed` | `Boolean` | If current user solved it (if user specific) |

---

## 🚀 Installation et Démarrage

### Pré-requis
*   Java 17+
*   Node.js & npm
*   MySQL

### Backend (Spring Boot)
```bash
# Clone the repository
git clone <url-du-repo>

# Navigate to backend directory
cd Regalis

# Run the application
./mvnw spring-boot:run
```

### Frontend (Angular)
```bash
# Navigate to frontend directory (to be created)
cd frontend-angular

# Install dependencies
npm install

# Run development server
ng serve
```
L'application sera accessible sur `http://localhost:4200`.
