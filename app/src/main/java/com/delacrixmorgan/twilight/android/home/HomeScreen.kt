package com.delacrixmorgan.twilight.android.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.delacrixmorgan.twilight.android.data.timezone.TimezoneRepository
import com.delacrixmorgan.twilight.android.data.timezone.localTime
import com.delacrixmorgan.twilight.android.ui.theme.Typography
import java.time.ZoneId

@Composable
fun HomeScreen(
    modifier: Modifier,
) {
    val state = rememberLazyListState()
    val list = remember {
        val savedTimezones = listOf(
            ZoneId.of("America/New_York"),
            ZoneId.of("Europe/London"),
            ZoneId.of("Asia/Kuala_Lumpur"),
            ZoneId.of("Australia/Melbourne"),
            ZoneId.of("Pacific/Auckland"),
        )
        TimezoneRepository.timezones
            .filter { it.zoneId in savedTimezones }
            .sortedBy { savedTimezones.indexOf(it.zoneId) }
    }

    Column(modifier) {
        Text(text = "Home", style = Typography.headlineLarge)
        LazyColumn(
            Modifier.fillMaxWidth(),
            state = state,
        ) {
            items(count = list.size, key = { list[it].zoneId.id }) { index ->
                val timezone = list[index]
                Row {
                    Column(Modifier.padding(vertical = 16.dp)) {
                        Text(
                            text = timezone.zone,
                            style = Typography.bodyLarge
                        )
                        Text(
                            text = timezone.city,
                            style = Typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.weight(1F))
                    Text(
                        text = timezone.localTime(),
                        Modifier
                            .padding(vertical = 16.dp)
                            .align(alignment = Alignment.CenterVertically),
                        style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Divider()
            }
        }
    }
}