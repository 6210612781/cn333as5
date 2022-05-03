package com.example.mycontactlist.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycontactlist.domain.model.ContactModel
import com.example.mycontactlist.routing.Screen
import com.example.mycontactlist.ui.components.AppDrawer
import com.example.mycontactlist.ui.components.Contact
import com.example.mycontactlist.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun VerifyScreen(viewModel: MainViewModel) {
    val contacts by viewModel.contactsNotInTrash.observeAsState(listOf())
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Verify Contacts",
                        color = MaterialTheme.colors.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch { scaffoldState.drawerState.open() }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "Drawer Button"
                        )
                    }
                }
            )
        },
        drawerContent = {
            AppDrawer(
                currentScreen = Screen.Contacts,
                closeDrawerAction = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onCreateNewContactClick() },
                contentColor = MaterialTheme.colors.background,
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Contact Button"
                    )
                }
            )
        }
    ) {
        if (contacts.isNotEmpty()) {
            VerifyList(
                contacts = contacts.sortedBy { it.name },
                onContactCheckedChange = {
                    viewModel.onContactCheckedChange(it)
                },
                onContactClick = { viewModel.onContactClick(it) }
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun VerifyList(
    contacts: List<ContactModel>,
    onContactCheckedChange: (ContactModel) -> Unit,
    onContactClick: (ContactModel) -> Unit
) {
    val Ver = contacts.filter{ it.isVerify }
    LazyColumn {
        items(count = Ver.size) { noteIndex ->
            val contact = Ver[noteIndex]
            Contact(
                contact = contact,
                onContactClick = onContactClick,
                onContactCheckedChange = onContactCheckedChange,
                isSelected = false
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
private fun FavoritesListPreview() {
    VerifyList(
        contacts = listOf(
            ContactModel(1, "B", "Content 1", null,false),
            ContactModel(2, "A", "Content 2", false, true),
            ContactModel(3, "F", "Content 3", true,true)
        ).sortedBy { it.name },
        onContactCheckedChange = {},
        onContactClick = {}
    )
}