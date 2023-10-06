package com.example.healthgrind.support

sealed class Screen(val route: String) {
    object Intro : Screen("intro")
    object Start : Screen("start")
    object Debug : Screen("debug")
    object SignUp : Screen("signup")
    object AllRewards : Screen("rewards")
    object Permission : Screen("permission")

    object Platforms : Screen("platforms")
    object Exercises : Screen("exercises")
    object Challenges : Screen("challenges")
    object Reward : Screen("reward")

    object Run : Screen("run")
    object Walk : Screen("walk")
    object Strength : Screen("strength")

    object PlayerInfo : Screen("playerInfo")
    object NameInput : Screen("nameInput")
    object AgeInput : Screen("ageInput")
    object GenderInput : Screen("genderInput")
    object SkillInput : Screen("skillInput")
    object WeightInput : Screen("weightInput")
    object HeightInput : Screen("heightInput")
    object ProfileInput : Screen("profileInput")

}