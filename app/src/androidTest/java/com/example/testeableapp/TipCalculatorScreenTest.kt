package com.example.testeableapp

import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipe
import androidx.compose.ui.test.swipeRight
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testeableapp.ui.Screens.TipCalculatorScreen
import com.example.testeableapp.ui.Screens.calculateTip
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TipCalculatorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testRoundUpChangesTip() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        // Se establece un monto para la cuenta
        composeTestRule.onNodeWithText("Monto de la cuenta").performTextInput("23.45")
        // Se verifica la existencia de la propina sin alterar
        composeTestRule.onNodeWithText("Propina: $3.52").assertExists()
        // Se busca el nodo que permita ser toogeable (la checkbox) y se da click
        composeTestRule.onNode(isToggleable()).performClick()
        // Se verifica que este activada le checkbox
        composeTestRule.onNode(isToggleable()).assertIsOn()
        // Se verifica el cambio en el valor con la nueva propina calculada
        composeTestRule.onNodeWithText("Propina: $4.00").assertExists()
    }


    // Función de ayuda que permite extraer el texto desde un composable
    private fun SemanticsNodeInteraction.getText(): String {
        val textList = this.fetchSemanticsNode().config.getOrNull(SemanticsProperties.Text)
        return textList?.joinToString("") { it.text } ?: ""
    }

    @Test
    fun testTipPercentageSliderChangesTip() {

        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        // Se agrega un monto inicial
        composeTestRule.onNodeWithText("Monto de la cuenta").performTextInput("100")

        // Se mueve el slider hasta llegar a 18%
        composeTestRule.onNodeWithTag("TipSlider").performTouchInput {
            val sliderWidth = width
            val twentyPercentPosition = (sliderWidth * (20f / 50f)).toInt()
            swipeRight(startX = 0f, endX = twentyPercentPosition.toFloat())
        }

        composeTestRule.waitForIdle()

        // Se verifica la existencia del text con el porcentaje de propina
        composeTestRule.onNodeWithTag("TipPercentageText")
            .assertTextContains("Porcentaje de propina: 18%")

        // Se calcula la propina con un 18% y se pasa a double (si no es así no sirve)
        val expectedTip = (100 * 18 / 100).toDouble()

        // Se verifica que la propina esperada sea mostrada
        composeTestRule.onNodeWithText("Propina: $%.2f".format(expectedTip)).assertExists()
    }


    @Test
    fun testUIElementsAreDisplayed() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        // Se comprueba la existencia del textField
        composeTestRule.onNodeWithText("Monto de la cuenta").assertExists()
        // Se comprueba la existencia del texto con el porcentaje de propina inicial
        composeTestRule.onNodeWithText("Porcentaje de propina: 15%").assertExists()
        // Se comprueba la existencia del numero de personas por defecto
        composeTestRule.onNodeWithText("Número de personas: 1").assertExists()
        // Se comprueba la existencia del texto de redondear la propina y la existencia de la checkbox
        composeTestRule.onNodeWithText("Redondear propina").assertExists()
        composeTestRule.onNode(isToggleable()).assertExists()
        // Se verifica la existencia dell texto de propina y total por persona
        composeTestRule.onNodeWithText("Propina: $0.00").assertIsDisplayed()
        composeTestRule.onNodeWithText("Total por persona: $0.00").assertIsDisplayed()
    }
}