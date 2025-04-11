package com.myapps.tgcf.paef.ui.viewmodel

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class TgcfViewModelTest {

    private lateinit var viewModel: TgcfViewModel

    @get:Rule
    private var instantExceutorRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        viewModel = TgcfViewModel()
    }

    /*
    Incio de la App. Aparece marcado el hombre, la edad a 0 y el slider de velocidad 0.0
     */
    @Test
    fun testInicialization() {
        assertTrue(viewModel.isMale)
        assertEquals(0, viewModel.ageDialog)
        assertEquals(Double.MAX_VALUE, viewModel.speedTime, 0.0)
    }

    //Open AgeDialog
    @Test
    fun openAgeDialogTest() {
        viewModel.openAgeDialog()
        assertTrue(viewModel.showAgeDialog)
    }

    //Close AgeDialog
    @Test
    fun closeAgeDialogTest() {
        viewModel.closeAgeDialog()
        assertFalse(viewModel.showAgeDialog)
    }

    //Actualizacion de la variable edad
    @Test
    fun updateAgeTempleteTest() {
        viewModel.updateAgeTemplete(25f)
        viewModel.confirmAgeSelect()
        assertEquals(25, viewModel.ageInput)
    }

    /*
    Confirmacion de todo el proceso desde que se abre AgeDialaog(), recoge la edad, y luego vuelve a cerrar el dialogo
     */
    @Test
    fun ageInput_closeDialogTrue() {
        viewModel.openAgeDialog()
        assertTrue(viewModel.showAgeDialog)
        viewModel.updateAgeTemplete(35f)
        viewModel.confirmAgeSelect()
        assertEquals(35, viewModel.ageInput)
        assertFalse(viewModel.showAgeDialog)
    }

    //Confirmacion del grupo de edad en el que deberia estar
    @Test
    fun calculateFinalScoreTest() {
        viewModel.ageInput = 55
        assertEquals(10, viewModel.ageGroup)
    }

    //Comprobacion que los valores se actualizan correctamente
    @Test
    fun updateScoresTest() {
        viewModel.ageInput = 30
        viewModel.pushCount = 25
        viewModel.absCount = 30
        viewModel.speedTime = 13.2
        viewModel.runTime = 2550

        viewModel.calculateFinalScore()
        viewModel.updateAllScores()

        assertTrue(viewModel.pushPoint >= 0)
        assertTrue(viewModel.absPoint >= 0)
        assertTrue(viewModel.speedPoint >= 0)
        assertTrue(viewModel.runPoint >= 0)
    }

}