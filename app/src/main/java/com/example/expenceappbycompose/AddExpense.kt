package com.example.expenceappbycompose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.expenceappbycompose.ApiCalling.AddResponse
import com.example.expenceappbycompose.ApiCalling.ApiClient
import com.example.expenceappbycompose.ApiCalling.ApiEndPoint
import com.example.expenceappbycompose.ApiCalling.GetExpensesResponse
import com.example.expenceappbycompose.ui.theme.ExpenceAppByComposeTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddExpense : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenceAppByComposeTheme {
                // A surface container using the 'background' color from the theme
                var itemName = remember{
                    mutableStateOf(TextFieldValue())
                }
                var price = remember{
                    mutableStateOf(TextFieldValue())
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp), // Adjust the horizontal padding as needed
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Add Your Expense")
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = itemName.value,
                            onValueChange = {
                                            itemName.value = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = price.value,
                            onValueChange = {
                                            price.value = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp)) // Adjust the vertical spacing between text fields and button
                        Button(
                            onClick = {
                                if(itemName.value.text == null || price.value.text == null){
                                    Toast.makeText(this@AddExpense, "Please enter your value", Toast.LENGTH_LONG).show()
                                }else{
                                    InsertExpense(itemName.value.text, price.value.text)
                                }
                                     },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text(text = "Add Expense")
                        }
                    }

                }
            }
        }
    }


    fun InsertExpense(itemName: String, price: String) {
        var apiEndPoint: ApiEndPoint
        var retrofit = ApiClient.getClient()
        apiEndPoint = retrofit.create(ApiEndPoint::class.java)
        apiEndPoint.UploadExpense(itemName, price.toInt()).enqueue(object : Callback<AddResponse> {
            override fun onResponse(call: Call<AddResponse>, response: Response<AddResponse>) {
                if (response.body()?.status == 1) {
                   finish()

                } else {
//                    Log.e("ApiTest", "Error in API response: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<AddResponse>, t: Throwable) {
                Toast.makeText(this@AddExpense, "${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }

        })
}
}
