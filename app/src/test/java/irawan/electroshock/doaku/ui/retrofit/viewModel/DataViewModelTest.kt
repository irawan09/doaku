package irawan.electroshock.doaku.ui.retrofit.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import irawan.electroshock.doaku.api.DataService
import irawan.electroshock.doaku.di.DataRepository
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.model.SerializedModel
import irawan.electroshock.doaku.utils.Resource
import irawan.electroshock.doaku.utils.Status
import irawan.electroshock.doaku.utils.TestCoroutineRule
import irawan.electroshock.doaku.view.use_case.DoaUseCase
import irawan.electroshock.doaku.view_model.DataViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DataViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var dataService: DataService

    @MockK
    private lateinit var useCase: DoaUseCase

    @MockK
    private lateinit var repository: DataRepository

    @MockK
    private lateinit var dataObserver: Observer<Resource<List<SerializedModel>>>

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldResponseSuccess() {
        testCoroutineRule.runBlockingTest {

            val listData = emptyList<SerializedModel>()
            val data = Resource<List<SerializedModel>>(Status.SUCCESS, listData,null)

            val listDataModel = listOf<DatabaseModel>(DatabaseModel("","","","",""))

            every { useCase.getFetchRemoteUseCase() } returns MutableLiveData<Resource<List<SerializedModel>>>(data)
            every { useCase.getDoaRemoteUseCase() } returns MutableLiveData<List<DatabaseModel>>(listDataModel)
            every { useCase.getDatabaseUseCase() } returns MutableLiveData<List<DatabaseModel>>(listDataModel)

            every { dataObserver.onChanged(any()) } answers {Resource.success(listData)}

            val viewModel = DataViewModel(useCase, repository)

            viewModel.getRemoteFetchData()?.observeForever(dataObserver)

            verify(atLeast = 1) { useCase.getFetchRemoteUseCase() }

            verify() { dataObserver.onChanged(Resource.success(listData)) }

            viewModel.getRemoteFetchData()?.removeObserver(dataObserver)

        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"

            every { useCase.getFetchRemoteUseCase() }.throws(RuntimeException(errorMessage))

            val viewModel = DataViewModel(useCase, repository)

            viewModel.getRemoteFetchData()?.observeForever(dataObserver)

            verify(atLeast = 1) { useCase.getFetchRemoteUseCase() }

            verify(exactly = 0) { dataObserver.onChanged(Resource.error(RuntimeException(errorMessage).toString())) }

            viewModel.getRemoteFetchData()?.removeObserver(dataObserver)
        }
    }

}