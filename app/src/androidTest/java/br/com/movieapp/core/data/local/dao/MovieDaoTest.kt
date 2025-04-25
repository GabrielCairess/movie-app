package br.com.movieapp.core.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import br.com.movieapp.core.data.local.MovieDatabase
import br.com.movieapp.core.data.local.entity.MovieEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class MovieDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: MovieDatabase

    private lateinit var dao: MovieDao

    val mockMovies = listOf(
        MovieEntity(movieId = 1, title = "Movie 1", imageUrl = ""),
        MovieEntity(movieId = 2, title = "Movie 2", imageUrl = ""),
        MovieEntity(movieId = 3, title = "Movie 3", imageUrl = "")
    )

    @Before
    fun setup() {
        hiltRule.inject()

        dao = database.movieDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun test_get_movies_should_return_list_of_movies() = runTest {
        mockMovies.forEach { dao.insertMovie(it) }
        val retrievedMovies = dao.getMovies().first()

        assertThat(retrievedMovies).isNotEmpty()
        assertThat(retrievedMovies.size).isEqualTo(mockMovies.size)
        assertThat(retrievedMovies).containsExactlyElementsIn(mockMovies)
    }

    @Test
    fun test_insert_movie_should_insert_movie() = runTest {
        val movie = MovieEntity(movieId = 1, title = "Movie 1", imageUrl = "")
        dao.insertMovie(movie)

        val retrievedMovies = dao.getMovies().first()
        assertThat(retrievedMovies).contains(movie)
    }

    @Test
    fun test_delete_movie_should_delete_movie() = runTest {
        val movie = MovieEntity(movieId = 1, title = "Movie 1", imageUrl = "")
        dao.insertMovie(movie)

        dao.deleteMovie(movie)

        val retrievedMovies = dao.getMovies().first()
        assertThat(retrievedMovies).doesNotContain(movie)
    }

    @Test
    fun test_is_favorite_should_return_movie() = runTest {
        val movie = MovieEntity(movieId = 1, title = "Movie 1", imageUrl = "")
        dao.insertMovie(movie)

        val isFavorite = dao.isFavorite(1)

        assertThat(isFavorite).isEqualTo(movie)
    }

    @Test
    fun test_is_favorite_should_not_return_movie() = runTest {
        val movieId = 1
        val movie = MovieEntity(movieId = movieId, title = "Movie 1", imageUrl = "")
        dao.insertMovie(movie)

        //searching with other movieId
        val isFavorite = dao.isFavorite(2)

        assertThat(isFavorite).isNotEqualTo(movie)
    }

    @Test
    fun test_when_movie_has_updates_should_insert() = runTest {
        val movie = MovieEntity(movieId = 1, title = "Movie 1", imageUrl = "")
        dao.insertMovie(movie)

        val updatedMovie = movie.copy(title = "Updated Movie")
        dao.insertMovie(updatedMovie)

        val retrievedMovies = dao.getMovies().first()
        assertThat(dao.getMovies().first().size).isEqualTo(1)
        assertThat(retrievedMovies).contains(updatedMovie)
    }

}