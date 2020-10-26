package com.phucduong.searchgitprofile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.phucduong.searchgitprofile.viewmodel.SearchUserViewModel
import com.phucduong.searchgitprofile.viewmodel.UserProfileViewModel
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
class TestProfileViewModel {
    private val testDispatcher = TestCoroutineDispatcher()
    // Subject under test
    private lateinit var profileViewModel: UserProfileViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var repository: FakeRepository

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        repository = FakeRepository()
        profileViewModel = UserProfileViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getProfileMatchWithMockData() {
        testDispatcher.runBlockingTest {
            profileViewModel.getUserProfile(FakeRepository.MOCK_LOGIN_USER_NAME)
            assertThat(profileViewModel.userProfile?.value?.email).isEqualTo(FakeRepository.MOCK_EMAIL)
            assertThat(profileViewModel.userProfile?.value?.name).isEqualTo(FakeRepository.MOCK_NAME)
            assertThat(profileViewModel.userProfile?.value?.login).isEqualTo(FakeRepository.MOCK_LOGIN_USER_NAME)
            assertThat(profileViewModel.userProfile?.value?.following).isEqualTo(FakeRepository.MOCK_FOLLOWING)
        }
    }
}