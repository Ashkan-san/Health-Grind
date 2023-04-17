package com.example.healthgrind.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthgrind.R
import com.example.healthgrind.firebase.database.challenge.Challenge
import com.example.healthgrind.firebase.database.reward.Reward
import com.example.healthgrind.firebase.database.reward.RewardViewModel


class DataSource() {
    @Composable
    fun getViewModel(viewModel: RewardViewModel = hiltViewModel()): State<List<Reward>> {
        val rewards = viewModel.rewards.collectAsState(initial = emptyList())
        return rewards
    }

    val challenges = listOf(
        Challenge(
            key = 0,
            title = "Run",
            reward = RewardModel(
                name = "Ryu",
                image = R.drawable.ryu
            ),
            icon = R.drawable.checkroom,
            exerciseType = ExerciseType.RUN,
            difficulty = SkillType.PRO,
            gameType = GameType.SMASH,
            goal = 60000 * 10
        ),
        Challenge(
            key = 1,
            title = "Run",
            reward = RewardModel(
                name = "Spirit Points",
                value = 1000,
                image = R.drawable.spiritpoints
            ),
            icon = R.drawable.euro,
            exerciseType = ExerciseType.RUN,
            difficulty = SkillType.ADVANCED,
            gameType = GameType.SMASH,
            goal = 60000 * 30
        ),
        Challenge(
            key = 2,
            title = "Steps Walk",
            reward = RewardModel(
                name = "DLC Pack 2",
                code = "C00VY3NDH3HR4BFT",
                image = R.drawable.dlcpack2
            ),
            icon = R.drawable.swords,
            exerciseType = ExerciseType.WALK,
            difficulty = SkillType.ADVANCED,
            gameType = GameType.SMASH,
            goal = 10000
        ),
        Challenge(
            key = 3,
            title = "Push Ups",
            reward = RewardModel(
                name = "Spirit Points",
                value = 5000,
                image = R.drawable.spiritpoints
            ),
            icon = R.drawable.euro,
            exerciseType = ExerciseType.STRENGTH,
            difficulty = SkillType.PRO,
            gameType = GameType.SMASH,
            goal = 50
        ),
        Challenge(
            key = 4,
            title = "Outdoor",
            reward = RewardModel(
                name = "Spirit Points",
                value = 500,
                image = R.drawable.spiritpoints
            ),
            icon = R.drawable.euro,
            exerciseType = ExerciseType.OUTDOOR,
            difficulty = SkillType.BEGINNER,
            gameType = GameType.SMASH,
            goal = 60000 * 10
        ),
        Challenge(
            key = 5,
            title = "Run",
            reward = RewardModel(
                name = "V-Bucks",
                code = "7Ö5-M1CH-31N",
                value = 1000,
                image = R.drawable.vbucks
            ),
            icon = R.drawable.euro,
            exerciseType = ExerciseType.RUN,
            difficulty = SkillType.BEGINNER,
            gameType = GameType.FORTNITE,
            goal = 60000 / 4
        ),
        Challenge(
            key = 6,
            title = "Sit Ups",
            reward = RewardModel(
                name = "V-Bucks",
                code = "14M4NVBUCKC0D3",
                value = 500,
                image = R.drawable.vbucks
            ),
            icon = R.drawable.euro,
            exerciseType = ExerciseType.STRENGTH,
            difficulty = SkillType.PRO,
            gameType = GameType.FORTNITE,
            goal = 100
        ),
        Challenge(
            key = 7,
            title = "Squats",
            reward = RewardModel(
                name = "V-Bucks",
                code = "14M4NVBUCKC0D3",
                value = 500,
                image = R.drawable.vbucks
            ),
            icon = R.drawable.euro,
            exerciseType = ExerciseType.STRENGTH,
            difficulty = SkillType.ADVANCED,
            gameType = GameType.FORTNITE,
            goal = 100
        ),
        Challenge(
            key = 8,
            title = "Run",
            reward = RewardModel(
                name = "Basketballer",
                image = R.drawable.sport_skin
            ),
            icon = R.drawable.checkroom,
            exerciseType = ExerciseType.RUN,
            difficulty = SkillType.PRO,
            gameType = GameType.FORTNITE,
            goal = 60000 * 60
        ),

        // PRÄSENTATIONS CHALLENGES
        Challenge(
            key = 9,
            title = "Push Ups",
            reward = RewardModel(
                name = "Saitama",
                image = R.drawable.saitama
            ),
            icon = R.drawable.checkroom,
            exerciseType = ExerciseType.STRENGTH,
            difficulty = SkillType.BEGINNER,
            gameType = GameType.FORTNITE,
            goal = 5
        ),
        Challenge(
            key = 10,
            title = "Outdoor",
            reward = RewardModel(
                name = "V-Bucks",
                code = "1-4M-4N-VBUCK-C0D3-T00",
                value = 500,
                image = R.drawable.vbucks
            ),
            icon = R.drawable.euro,
            exerciseType = ExerciseType.OUTDOOR,
            difficulty = SkillType.BEGINNER,
            gameType = GameType.FORTNITE,
            goal = 60000 * 1
        ),
        Challenge(
            key = 11,
            title = "Steps Walk",
            reward = RewardModel(
                name = "Weapon",
                image = R.drawable.weapon
            ),
            icon = R.drawable.swords,
            exerciseType = ExerciseType.WALK,
            difficulty = SkillType.BEGINNER,
            gameType = GameType.FORTNITE,
            goal = 1000
        ),
    )

    val games = listOf(
        GameModel(
            id = 1,
            name = "Smash Bros Ultimate",
            image = R.drawable.smash,
            challenges = challenges
        ),
        GameModel(
            id = 2,
            name = "Fortnite",
            image = R.drawable.fortnite
        ),
        GameModel(
            id = 3,
            name = "Stardew Valley",
            image = R.drawable.stardew
        ),
        GameModel(
            id = 4,
            name = "Tekken",
            image = R.drawable.tekken
        ),
        GameModel(
            id = 5,
            name = "Valorant",
            image = R.drawable.valorant
        ),
    )
}