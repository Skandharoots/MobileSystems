package com.example.lab_application

import android.content.Context
import com.example.lab_application.adapter.ItemAdapter
import com.example.lab_application.data.DataSource
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock

class AffirmationAdapterTest {

    private val context = mock(Context::class.java)

    @Test
    fun adapter_size() {
        val list = DataSource().loadAffirmations()
        val itemAdapter = ItemAdapter(context, list)
        assertEquals("ItemAdapter is not the correct size", list.size, itemAdapter.itemCount)
    }

}