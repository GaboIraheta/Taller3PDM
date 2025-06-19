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

    // Verifica que el total por persona se actualiza correctamente al cambiar el número de personas
    @Test
    fun testTotalPorPersonaActualizaCorrectamente() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule.onNodeWithText("Monto de la cuenta").performTextInput("100")
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Total por persona: \$57.50").assertExists()
    }
}
