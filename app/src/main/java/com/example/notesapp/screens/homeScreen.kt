package com.example.notesapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notesapp.model.NoteEntity
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(nav: NavHostController, vm: NoteViewModel) {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            Modifier
                .padding( 8.dp)
        ) {

            Row(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text("Notes", fontSize = 48.sp, fontWeight = FontWeight.Bold)

                Button(
                    modifier = Modifier
                        .size(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        vm.insertNote("")
                    }
                ) {}
            }

            HorizontalDivider(
                Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.tertiary
            )

            val isAsc by rememberSaveable { mutableStateOf(true) }
            val notesAsc by vm.notesAsc.collectAsState(listOf())
            val notesDesc by vm.notesDesc.collectAsState(listOf())

            LazyColumn(
                Modifier.weight(1f)
            ) {
                if (isAsc) {
                    for (note in notesAsc) {
                        item {
                            NoteDataDisplay(note,
                                {note, text -> vm.updateNote(note, text)},
                                {note -> vm.deleteNote(note)}
                            )
                        }
                    }
                } else {
                    for (note in notesDesc) {
                        item {
                            NoteDataDisplay(
                                note,
                                {note, text -> vm.updateNote(note, text)},
                                {vm.deleteNote(it)}
                            )
                        }
                    }
                }
            }

            HorizontalDivider(
                Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.tertiary
            )

            Spacer(
                Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            )
        }
    }
}

@Composable
fun NoteDataDisplay(note: NoteEntity, editCallback: (NoteEntity, String) -> Unit, deleteCallback: (NoteEntity) -> Unit) {

    Column(
        Modifier
            .padding(8.dp, 8.dp)
            .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(8.dp))
    ) {

        var isEditing by rememberSaveable { mutableStateOf(false) }
        var editedText by rememberSaveable { mutableStateOf(note.text) }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp, 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            val formatter = SimpleDateFormat("h:mm E, MMM dd yyyy", Locale.getDefault())
            Text(formatter.format(note.timestamp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    modifier = Modifier
                        .size(24.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        if (isEditing) {
                            editCallback(note, editedText)
                        }
                        isEditing = !isEditing
                    }
                ) {}

                Button(
                    modifier = Modifier
                        .size(24.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        deleteCallback(note)
                    }
                ) {}
            }
        }

        Row(
            Modifier
                .padding(8.dp, 4.dp)
        ) {
            if (isEditing) {
                OutlinedTextField(
                    value = editedText,
                    onValueChange = { editedText= it }
                )
            } else {
                Text(note.text)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteDataDisplayPreview() {
    NotesAppTheme {
        NoteDataDisplay(NoteEntity(Date(),"test text"), {a, b ->}, {})
    }
}
