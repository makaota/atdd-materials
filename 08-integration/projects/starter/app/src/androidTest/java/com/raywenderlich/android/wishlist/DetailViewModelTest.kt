package com.raywenderlich.android.wishlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raywenderlich.android.wishlist.persistance.RepositoryImpl
import com.raywenderlich.android.wishlist.persistance.WishlistDao
import com.raywenderlich.android.wishlist.persistance.WishlistDaoImpl
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import androidx.lifecycle.Observer

// @RunWith(AndroidJUnit4::class)
class DetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val wishlistDao: WishlistDao = Mockito.spy(WishlistDaoImpl())

    private val viewModel = DetailViewModel(RepositoryImpl(wishlistDao))

    @Test
    fun saveNewItemCallsDatabase(){
        viewModel.saveNewItem(Wishlist("Victoria",
            listOf("RW Android Apprentice Book","Android Phone"),
            1),
            "Smart watch")

        verify(wishlistDao).save(any())
    }

    @Test
    fun saveNewItemSavesData(){
        val wishlist = Wishlist("Victoria",
            listOf("RW Android Apprentice Book", "Android phone"), 1)

        val name = "Smart watch"
        viewModel.saveNewItem(wishlist,name)

        val mockObserver = mock<Observer<Wishlist>>()

        wishlistDao.findById(wishlist.id).observeForever(mockObserver)
        verify(mockObserver).onChanged(wishlist.copy(wishes = wishlist.wishes + name))
    }

    @Test
    fun getWishListCallsDatabase(){
        viewModel.getWishlist(1)
        verify(wishlistDao).findById(any())
    }

    @Test
    fun getWishListReturnsCorrectData(){
        val wishlist = Wishlist("Victoria",
            listOf("RW Android Apprentice Book", "Android phone"), 1)
        wishlistDao.save(wishlist)
        val mockObserver = mock<Observer<Wishlist>>()
        viewModel.getWishlist(1).observeForever(mockObserver)
        verify(mockObserver).onChanged(wishlist)
    }
}