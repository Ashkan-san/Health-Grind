package com.example.healthgrind.presentation.screens

sealed class Screen(val route: String) {
    object FirstStart : Screen("first")
    object Start : Screen("start")
    object Debug : Screen("debug")
    object SignUp : Screen("signup")

    object Platforms : Screen("platforms")
    object Exercises : Screen("exercises")
    object Challenges : Screen("challenges")
    object TestReward : Screen("rewards")

    object Running : Screen("running")
    object Walk : Screen("walk")
    object Strength : Screen("strength")
    object RewardDialog : Screen("rewardDialog")

    // TODO TEST
    object Training : Screen("training")

    object PlayerInfo : Screen("playerInfo")
    object NameInput : Screen("nameInput")
    object AgeInput : Screen("ageInput")
    object GenderInput : Screen("genderInput")
    object SkillInput : Screen("skillInput")
    object WeightInput : Screen("weightInput")
    object HeightInput : Screen("heightInput")
    object ProfileInput : Screen("profileInput")

}