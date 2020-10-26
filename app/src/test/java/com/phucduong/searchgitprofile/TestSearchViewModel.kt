package com.phucduong.searchgitprofile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.phucduong.searchgitprofile.viewmodel.SearchUserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TestSearchViewModel {
    private val testDispatcher = TestCoroutineDispatcher()
    // Subject under test
    private lateinit var searchUserViewModel: SearchUserViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var repository: FakeRepository

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        repository = FakeRepository()
        searchUserViewModel = SearchUserViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun searchKeywordAndGetCorrectMockData() {
        testDispatcher.runBlockingTest {
            searchUserViewModel.searchUserWithQuery()
            testDispatcher.advanceTimeBy(600L)
            //Assert result match with mock data
            assertThat(searchUserViewModel.listUser.value?.size).isEqualTo(1)
            assertThat(searchUserViewModel.listUser.value?.first()?.login).isEqualTo(FakeRepository.MOCK_LOGIN_USER_NAME)
            assertThat(searchUserViewModel.listUser.value?.first()?.query).isEqualTo(FakeRepository.MOCK_SEARCH_KEYWORD)
        }
    }

    @Test
    fun callPagingWithSameKeywordPagenumberIncrease() {
        testDispatcher.runBlockingTest {
            searchUserViewModel.searchUserWithQuery()
            testDispatcher.advanceTimeBy(600L)
            searchUserViewModel.searchUserWithQuery()
            testDispatcher.advanceTimeBy(600L)
            //Assert result match with mock data
            assertThat(searchUserViewModel.listUser.value?.size).isEqualTo(2)
        }
    }

}