package com.example.mycontactlist.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycontactlist.database.AppDatabase
import com.example.mycontactlist.database.DbMapper
import com.example.mycontactlist.database.Repository

import com.example.mycontactlist.domain.model.ContactModel
import com.example.mycontactlist.domain.model.TagModel
import com.example.mycontactlist.routing.PhoneNumberRouter
import com.example.mycontactlist.routing.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : ViewModel() {
    val contactsNotInTrash: LiveData<List<ContactModel>> by lazy {
        repository.getAllContactsNotInTrash()
    }

    private var _contactEntry = MutableLiveData(ContactModel())

    val contactEntry: LiveData<ContactModel> = _contactEntry

    val tags: LiveData<List<TagModel>> by lazy {
        repository.getAllTags()
    }

    val contactsInTrash by lazy { repository.getAllContactsInTrash() }

    private var _selectedContacts = MutableLiveData<List<ContactModel>>(listOf())

    val selectedContacts: LiveData<List<ContactModel>> = _selectedContacts

    private val repository: Repository

    init {
        val db = AppDatabase.getInstance(application)
        repository = Repository(db.contactDao(), db.tagDao(), DbMapper())
    }

    fun onCreateNewContactClick() {
        _contactEntry.value = ContactModel()
        PhoneNumberRouter.navigateTo(Screen.SaveContact)
    }

    fun onContactClick(contact: ContactModel) {
        _contactEntry.value = contact
        PhoneNumberRouter.navigateTo(Screen.SaveContact)
    }

    fun onContactCheckedChange(contact: ContactModel) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.insertContact(contact)
        }
    }

    fun onContactSelected(contact: ContactModel) {
        _selectedContacts.value = _selectedContacts.value!!.toMutableList().apply {
            if (contains(contact)) {
                remove(contact)
            } else {
                add(contact)
            }
        }
    }

    fun restoreContacts(contacts: List<ContactModel>) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.restoreContactsFromTrash(contacts.map { it.id })
            withContext(Dispatchers.Main) {
                _selectedContacts.value = listOf()
            }
        }
    }

    fun permanentlyDeleteContacts(contacts: List<ContactModel>) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.deleteContacts(contacts.map { it.id })
            withContext(Dispatchers.Main) {
                _selectedContacts.value = listOf()
            }
        }
    }

    fun onContactEntryChange(contact: ContactModel) {
        _contactEntry.value = contact
    }

    fun saveContact(contact: ContactModel) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.insertContact(contact)

            withContext(Dispatchers.Main) {
                PhoneNumberRouter.navigateTo(Screen.Contacts)

                _contactEntry.value = ContactModel()
            }
        }
    }

    fun moveContactToTrash(contact: ContactModel) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.moveContactToTrash(contact.id)

            withContext(Dispatchers.Main) {
                PhoneNumberRouter.navigateTo(Screen.Contacts)
            }
        }
    }
}