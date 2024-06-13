package com.example.studienarbeitapp.helper

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ServiceHelperTest {

    @Before
    fun setup() {
        // Clear the storage map before each test
        ServiceHelper.clearServiceHelperStorage()
    }

    @After
    fun teardown() {
        // Clear the storage map after each test
        ServiceHelper.clearServiceHelperStorage()
    }


    @Test
    fun testSaveAndGetVehicleLoaded() {
        ServiceHelper.saveVehicleLoaded(true)
        assertTrue(ServiceHelper.getVehicleLoaded() ?: false)

        ServiceHelper.saveVehicleLoaded(false)
        assertFalse(ServiceHelper.getVehicleLoaded() ?: true)
    }

    @Test
    fun testSaveAndGetDeploymentOverallLoaded() {
        ServiceHelper.saveDeploymentOverallLoaded(true)
        assertTrue(ServiceHelper.getDeploymentOverallLoaded() ?: false)

        ServiceHelper.saveDeploymentOverallLoaded(false)
        assertFalse(ServiceHelper.getDeploymentOverallLoaded() ?: true)
    }

    @Test
    fun testSaveAndGetPatientInformationLoaded() {
        ServiceHelper.savePatientInformationLoaded(true)
        assertTrue(ServiceHelper.getPatientInformationLoaded() ?: false)

        ServiceHelper.savePatientInformationLoaded(false)
        assertFalse(ServiceHelper.getPatientInformationLoaded() ?: true)
    }

    @Test
    fun testSaveAndGetDeploymentInformationLoaded() {
        ServiceHelper.saveDeploymentInformationLoaded(true)
        assertTrue(ServiceHelper.getDeploymentInformationLoaded() ?: false)

        ServiceHelper.saveDeploymentInformationLoaded(false)
        assertFalse(ServiceHelper.getDeploymentInformationLoaded() ?: true)
    }

    @Test
    fun testSaveAndGetDeploymentTimesLoaded() {
        ServiceHelper.saveDeploymentTimesLoaded(true)
        assertTrue(ServiceHelper.getDeploymentTimesLoaded() ?: false)

        ServiceHelper.saveDeploymentTimesLoaded(false)
        assertFalse(ServiceHelper.getDeploymentTimesLoaded() ?: true)
    }

    @Test
    fun testClearServiceHelperStorage() {
        ServiceHelper.saveVehicleLoaded(true)
        ServiceHelper.saveDeploymentOverallLoaded(true)
        ServiceHelper.savePatientInformationLoaded(true)
        ServiceHelper.saveDeploymentInformationLoaded(true)
        ServiceHelper.saveDeploymentTimesLoaded(true)

        ServiceHelper.clearServiceHelperStorage()

        assertFalse(ServiceHelper.getVehicleLoaded() ?: true)
        assertFalse(ServiceHelper.getDeploymentOverallLoaded() ?: true)
        assertFalse(ServiceHelper.getPatientInformationLoaded() ?: true)
        assertFalse(ServiceHelper.getDeploymentInformationLoaded() ?: true)
        assertFalse(ServiceHelper.getDeploymentTimesLoaded() ?: true)
    }
}
