package com.example.testeableapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testeableapp.ui.Screens.TipCalculatorScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TipCalculatorScreenTestAditionals {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Verifica que no se pueda disminuir el número de personas por debajo de 1
    @Test
    fun testNoDisminuirPersonasPorDebajoDeUno() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule.onNodeWithText("-").performClick()
        composeTestRule.onNodeWithText("1").assertExists()
    }

    @Test
    fun testTotalPorPersonaActualizaCorrectamente() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule.onNodeWithTag("inputMonto").performTextInput("100")
        composeTestRule.onNodeWithTag("btnMas").performClick()

        // Esperar a que Compose se estabilice
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("txtTotalPorPersona")
            .assertTextContains("Total por persona: $57.50")
    }

}
