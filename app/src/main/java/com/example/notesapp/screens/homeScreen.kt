package com.example.notesapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notesapp.R
import com.example.notesapp.model.NoteEntity
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(nav: NavHostController, vm: NoteViewModel) {

    var isAsc by rememberSaveable { mutableStateOf(true) }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            Modifier
                .padding( 8.dp)
        ) {

            Spacer(
                Modifier
                    .windowInsetsTopHeight(WindowInsets.systemBars)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            )

            Row(
                Modifier
                    .padding(16.dp, 4.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text("Notes", fontSize = 48.sp, fontWeight = FontWeight.Bold)

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Button(
                        modifier = Modifier
                            .size(48.dp),
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.Black
                        ),
                        onClick = {
                            isAsc = !isAsc
                        }
                    ) {
                        if (isAsc) {
                            Image(
                                painter = painterResource(R.drawable.arrow_square_up_svgrepo_com),
                                contentDescription = "Up symbol. Source: https://www.svgrepo.com/svg/533629/arrow-square-up"
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.arrow_square_down_svgrepo_com),
                                contentDescription = "Down symbol. Source: https://www.svgrepo.com/svg/533622/arrow-square-down"
                            )
                        }
                    }

                    Button(
                        modifier = Modifier
                            .size(48.dp),
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.Black
                            ),
                        onClick = {
                            vm.insertNote("")
                        }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.square_medical_svgrepo_com),
                            contentDescription = "Plus symbol. Source: https://www.svgrepo.com/svg/532482/square-medical"
                        )
                    }
            }
            }

            HorizontalDivider(
                Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary
            )

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
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(
                Modifier
                    .windowInsetsBottomHeight(WindowInsets.systemBars)
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
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(8.dp))
    ) {

        var isEditing by rememberSaveable { mutableStateOf(false) }
        var editedText by rememberSaveable { mutableStateOf(note.text) }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            val formatter = SimpleDateFormat("h:mm MM/dd/yyyy", Locale.getDefault())
            Text(formatter.format(note.timestamp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    modifier = Modifier
                        .size(48.dp, 24.dp),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface,
                        disabledContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    onClick = {
                        if (isEditing && editedText != note.text) {
                            editCallback(note, editedText)
                        }
                        isEditing = !isEditing
                    }
                ) {
                    Image(
                        painter = painterResource(R.drawable.pen_square_svgrepo_com),
                        contentDescription = "Pen symbol. Source: https://www.svgrepo.com/svg/532995/pen-square"
                    )
                }

                Button(
                    modifier = Modifier
                        .size(48.dp, 24.dp),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                        disabledContainerColor = MaterialTheme.colorScheme.onTertiary,
                        disabledContentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = {
                        deleteCallback(note)
                    }
                ) {
                    Image(
                        painter = painterResource(R.drawable.trash_svgrepo_com),
                        contentDescription = "X symbol. Source: https://www.svgrepo.com/svg/533007/trash"
                    )
                }
            }
        }

        Row(
            Modifier
                .padding(8.dp, 0.dp)
        ) {
            if (isEditing) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    value = editedText,
                    onValueChange = { editedText= it }
                )
            } else {
                Text(
                    note.text,
                    Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteDataDisplayPreview() {
    NotesAppTheme {
        NoteDataDisplay(NoteEntity(Date(),"test text"), {a, b -> {}}, {})
    }
}
