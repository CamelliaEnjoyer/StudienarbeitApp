package com.example.studienarbeitapp.helper

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class StorageHelperTest {

    @Before
    fun setup() {
        // Clear the storage map before each test
        StorageHelper.clearStorageHelperStorage()
    }

    @After
    fun teardown() {
        // Clear the storage map after each test
        StorageHelper.clearStorageHelperStorage()
    }

    @Test
    fun testSaveAndGetDeploymentId() {
        val deploymentId = "deployment123"
        StorageHelper.saveDeploymentId(deploymentId)
        assertEquals(deploymentId, StorageHelper.getDeploymentId())
    }

    @Test
    fun testSaveAndGetToken() {
        val token = "token123"
        StorageHelper.saveToken(token)
        assertEquals(token, StorageHelper.getToken())
    }

    @Test
    fun testSaveAndGetVehicle() {
        val vehicle = "vehicle123"
        StorageHelper.saveVehicle(vehicle)
        assertEquals(vehicle, StorageHelper.getVehicleID())
    }

    @Test
    fun testSaveAndGetDeploymentInfoId() {
        val deploymentInfoId = "deploymentInfo123"
        StorageHelper.saveDeploymentInfoId(deploymentInfoId)
        assertEquals(deploymentInfoId, StorageHelper.getDeploymentInfoId())
    }

    @Test
    fun testSaveAndGetPatientInfoId() {
        val patientInfoId = "patientInfo123"
        StorageHelper.savePatientInfoId(patientInfoId)
        assertEquals(patientInfoId, StorageHelper.getPatientInfoId())
    }

    @Test
    fun testSaveAndGetTimeModelId() {
        val timeModelId = "timeModel123"
        StorageHelper.saveTimeModelId(timeModelId)
        assertEquals(timeModelId, StorageHelper.getTimeModelId())
    }

    @Test
    fun testClearDeploymentInformation() {
        StorageHelper.saveToken("token123")
        StorageHelper.saveVehicle("vehicle123")
        StorageHelper.clearDeploymentInformation()
        assertEquals("", StorageHelper.getToken())
        assertEquals("", StorageHelper.getVehicleID())
    }

    @Test
    fun testClearStorageHelperStorage() {
        StorageHelper.saveDeploymentId("deployment123")
        StorageHelper.saveToken("token123")
        StorageHelper.saveVehicle("vehicle123")
        StorageHelper.saveDeploymentInfoId("deploymentInfo123")
        StorageHelper.savePatientInfoId("patientInfo123")
        StorageHelper.saveTimeModelId("timeModel123")

        StorageHelper.clearStorageHelperStorage()

        assertEquals("", StorageHelper.getDeploymentId())
        assertEquals("", StorageHelper.getToken())
        assertEquals("", StorageHelper.getVehicleID())
        assertEquals("", StorageHelper.getDeploymentInfoId())
        assertEquals("", StorageHelper.getPatientInfoId())
        assertEquals("", StorageHelper.getTimeModelId())
    }
}
