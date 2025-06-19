package com.example.testeableapp

import com.example.testeableapp.ui.Screens.calculateTip
import org.junit.Test
import kotlin.test.assertEquals

class AditionalUnitTests {
    @Test
    fun `tip with 0 percent`() {
        val tip = calculateTip(
            amount = 50.0,
            tipPercent = 0,
            roundUp = false
        )
        assertEquals(
            expected = 0.0,
            actual = tip,
            message = "La propina con 0% es, como se espera, $0.00"
            //quería ver el mensaje :(
        )
    }

    @Test
    // al parecer 0.00 es ilegal?????
    fun `total amount is 0 dollars with 0 cents`(){
        var bill = 0.00
        var tip = calculateTip(bill, 15, false) //this should be zero, btw
        var numberOfPeople = 3

        // copiamos el totalPerPerson directamente del tipCalculatorScreen (no sé si se hace así)
        val totalPerPerson = if (numberOfPeople > 0) (bill + tip) / numberOfPeople else 0.0

        assertEquals(
            expected = 0.0,
            actual = totalPerPerson,
            message = "Si el monto a pagar es 0, no se debería de cobrar nada."
        )
    }
}