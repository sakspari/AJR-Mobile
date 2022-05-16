package com.example.atmajayarental.ui.home.profil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.atmajayarental.R
import com.example.atmajayarental.data.api.UrlDataSource
import com.example.atmajayarental.data.api.model.Customer
import com.example.atmajayarental.ui.components.ProfileImage

@Composable
fun CustomerProfile(customer: Customer?) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            customer?.let {
                ProfileImage(imgUrl = "${UrlDataSource.PUBLIC}${it.picture}")
            }

            Spacer(modifier = Modifier.width(4.dp))

            customer?.let {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        customer?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_card_membership_24),
                    contentDescription = "person icon",
                    modifier = Modifier,
                    colorFilter = ColorFilter.tint(color = Color.Blue)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "ID: ${it.id}",
                    style = MaterialTheme.typography.body1,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        customer?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_outline_person_outline_24),
                    contentDescription = "person icon",
                    modifier = Modifier,
                    colorFilter = ColorFilter.tint(color = Color.Blue)
                )
                Spacer(modifier = Modifier.width(4.dp))
                customer?.let {
                    Text(
                        text = "Nama: ${it.name}",
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }

        customer?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_outline_email_24),
                    contentDescription = "person icon",
                    modifier = Modifier,
                    colorFilter = ColorFilter.tint(color = Color.Blue)
                )
                Spacer(modifier = Modifier.width(4.dp))
                customer?.let {
                    Text(
                        text = "Email: ${it.email}",
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }

        customer?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_outline_supervised_user_circle_24),
                    contentDescription = "person icon",
                    modifier = Modifier,
                    colorFilter = ColorFilter.tint(color = Color.Blue)
                )
                Spacer(modifier = Modifier.width(4.dp))
                customer?.let {
                    Text(
                        text = if (it.gender == 1) "Gender: Pria" else "Gender: Wanita",
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }

        customer?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_outline_date_range_24),
                    contentDescription = "person icon",
                    modifier = Modifier,
                    colorFilter = ColorFilter.tint(color = Color.Blue)
                )
                Spacer(modifier = Modifier.width(4.dp))
                customer?.let {
                    Text(
                        text = "Tanggal Lahir: ${it.birthdate}",
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }

        customer?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_outline_call_24),
                    contentDescription = "person icon",
                    modifier = Modifier,
                    colorFilter = ColorFilter.tint(color = Color.Blue)
                )
                Spacer(modifier = Modifier.width(4.dp))
                customer?.let {
                    Text(
                        text = "Telepon: ${it.phone}",
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }

        customer?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_outline_home_24),
                    contentDescription = "person icon",
                    modifier = Modifier,
                    colorFilter = ColorFilter.tint(color = Color.Blue)
                )
                Spacer(modifier = Modifier.width(4.dp))
                customer?.let {
                    Text(
                        text = "Alamat: ${it.address}",
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

    }
}