package com.example.testeableapp

import com.example.testeableapp.ui.Screens.calculateTip
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.ceil

class TipCalculatorTest {

    @Test
    fun `calculo con 20 por ciento de propina`() {
        val result = calculateTip(amount = 100.0, tipPercent = 20, roundUp = false)
        assertEquals(20.0, result, 0.01)
    }

    @Test
    fun `calculo con 15 por ciento y redondeo activo`() {
        val result = calculateTip(amount = 85.0, tipPercent = 15, roundUp = true)
        val expected = ceil(85.0 * 0.15)
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `calculo con monto negativo debe dar 0`() {
        val result = calculateTip(amount = -50.0, tipPercent = 20, roundUp = false)
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun `calculo total por persona incluye propina`() {
        val amount = 120.0
        val tipPercent = 10
        val numPeople = 4
        val tip = calculateTip(amount, tipPercent, roundUp = false)
        val totalPerPerson = (amount + tip) / numPeople
        assertEquals(33.0, totalPerPerson, 0.01) //(amount = 120 + tip = 12) / numPeople = 4 = 33.0
    }
}
