package com.shijen.a4o

import com.shijen.a4o.data.JokeRepository
import javax.inject.Inject

class GetJokeUseCase @Inject constructor(private val jokeRepository: JokeRepository) {
    suspend operator fun invoke() = jokeRepository.getJoke()
}