package com.example.healthgrind.support

enum class ExerciseType {
    RUN, WALK, STRENGTH, OUTDOOR
}

enum class SkillType {
    BEGINNER, ADVANCED, PRO;

    companion object {
        fun getList(): List<String> {
            return values().map {
                it.toString()
            }
        }
    }
}

enum class GenderType {
    MALE, FEMALE, OTHER;

    companion object {
        fun getList(): List<String> {
            return values().map {
                it.toString()
            }
        }
    }
}

enum class ProfileType {
    ARDIAN, BRIAN, GERRIT, JILL, JOSEPH, JUSTUS, KJELL, LEO, OLIVER, QUAN;

    companion object {
        fun getList(): List<String> {
            return values().map {
                it.toString()
            }
        }
    }
}