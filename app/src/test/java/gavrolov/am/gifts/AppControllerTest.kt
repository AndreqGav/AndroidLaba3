package gavrolov.am.gifts

import gavrolov.am.gifts.app.controllers.AppController
import org.junit.Assert.*
import org.junit.Test

class AppControllerTest {

    @Test
    fun createUser_isCorrect() {
        // arrange
        val controller = AppController()

        // act
        val id = controller.createUser("test user")

        // assert
        val user = controller.getUserList().users.find { it.id == id }!!

        assertNotNull(user)
        assertEquals("test user", user.name)
        assertEquals(0, user.cost)
    }

    @Test
    fun renameUser_isCorrect() {
        // arrange
        val controller = AppController()
        val id = controller.createUser("test user")
        val user = controller.getUserList().users.find { it.id == id }!!

        // act
        controller.renameUser(user.id, "new test name")

        // assert
        val renamedUser = controller.getUserList().users.find { it.id == id }!!

        assertNotNull(renamedUser)
        assertEquals("new test name", renamedUser.name)
        assertEquals(user.cost, renamedUser.cost)
    }

    @Test
    fun deleteUser_isCorrect() {
        // arrange
        val controller = AppController()
        val id = controller.createUser("test user")
        val user = controller.getUserList().users.find { it.id == id }!!

        // act
        controller.deleteUser(user.id)

        // assert
        val exists = controller.getUserList().users.any { it.id == id }

        assertFalse(exists)
    }

    @Test
    fun cancelDeleteUser_isCorrect() {
        // arrange
        val controller = AppController()
        val id = controller.createUser("test user")
        val user = controller.getUserList().users.find { it.id == id }!!
        controller.deleteUser(user.id)

        // act
        controller.cancelDeleteUser(user.id)

        // assert
        val exists = controller.getUserList().users.any { it.id == id }

        assertTrue(exists)
    }

    @Test
    fun createGift_isCorrect() {
        // arrange
        val controller = AppController()
        val userId = controller.createUser("test user")
        val user = controller.getUserList().users.find { it.id == userId }!!

        // act
        val id = controller.createGift(user.id, "test Name", 100, "Test Description", 10)

        // assert
        val card = controller.getUserCard(user.id)
        val gift = card.gifts.find { it.id == id }!!

        assertNotNull(user)
        assertEquals("test Name", gift.name)
        assertEquals("Test Description", gift.description)
        assertEquals(100, gift.cost)
        assertEquals(10, gift.count)
    }

    @Test
    fun updateGift_isCorrect() {
        // arrange
        val controller = AppController()
        val userId = controller.createUser("test user")
        val user = controller.getUserList().users.find { it.id == userId }!!
        val id = controller.createGift(user.id, "test Name", 100, "Test Description", 10)

        // act
        controller.updateGift(id, "new test name", 500, "New Test Description", 5)

        // assert
        val newCard = controller.getUserCard(user.id)
        val updatedGift = newCard.gifts.find { it.id == id }!!

        assertNotNull(updatedGift)
        assertEquals("new test name", updatedGift.name)
        assertEquals("New Test Description", updatedGift.description)
        assertEquals(500, updatedGift.cost)
        assertEquals(5, updatedGift.count)
    }

    @Test
    fun increaseGift_isCorrect() {
        // arrange
        val controller = AppController()
        val userId = controller.createUser("test user")
        val user = controller.getUserList().users.find { it.id == userId }!!
        val id = controller.createGift(user.id, "test Name", 100, "Test Description", 10)
        val card = controller.getUserCard(user.id)
        val gift = card.gifts.find { it.id == id }!!

        // act
        controller.increaseGift(id)

        // assert
        val newCard = controller.getUserCard(user.id)
        val updatedGift = newCard.gifts.find { it.id == id }!!

        assertNotNull(updatedGift)
        assertEquals(gift.count + 1, updatedGift.count)
    }

    @Test
    fun decreaseGift_isCorrect() {
        // arrange
        val controller = AppController()
        val userId = controller.createUser("test user")
        val user = controller.getUserList().users.find { it.id == userId }!!
        val id = controller.createGift(user.id, "test Name", 100, "Test Description", 10)
        val card = controller.getUserCard(user.id)
        val gift = card.gifts.find { it.id == id }!!

        // act
        controller.decreaseGift(id)

        // assert
        val newCard = controller.getUserCard(user.id)
        val updatedGift = newCard.gifts.find { it.id == id }!!

        assertNotNull(updatedGift)
        assertEquals(gift.count - 1, updatedGift.count)
    }

    @Test
    fun deleteGift_isCorrect() {
        // arrange
        val controller = AppController()
        val userId = controller.createUser("test user")
        val user = controller.getUserList().users.find { it.id == userId }!!
        val id = controller.createGift(user.id, "test Name", 100, "Test Description", 10)

        // act
        controller.deleteGift(id)

        // assert
        val newCard = controller.getUserCard(user.id)
        val exists = newCard.gifts.any { it.id == id }

        assertFalse(exists)
    }

    @Test
    fun cancelDeleteGift_isCorrect() {
        // arrange
        val controller = AppController()
        val userId = controller.createUser("test user")
        val user = controller.getUserList().users.find { it.id == userId }!!
        val id = controller.createGift(user.id, "test Name", 100, "Test Description", 10)
        controller.deleteGift(id)

        // act
        controller.cancelDeleteGift(id)

        // assert
        val newCard = controller.getUserCard(user.id)
        val exists = newCard.gifts.any { it.id == id }

        assertTrue(exists)
    }
}