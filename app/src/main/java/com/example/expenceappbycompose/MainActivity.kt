package com.example.expenceappbycompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.expenceappbycompose.ApiCalling.AddResponse
import com.example.expenceappbycompose.ApiCalling.ApiClient
import com.example.expenceappbycompose.ApiCalling.ApiEndPoint
import com.example.expenceappbycompose.ApiCalling.GetExpensesResponse
import com.example.expenceappbycompose.ApiCalling.Message
import com.example.expenceappbycompose.ui.theme.ExpenceAppByComposeTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private var dataList = mutableStateOf<ArrayList<Message>>(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        var isDataLoader = remember {
            mutableStateOf(false)
        }
            ExpenceAppByComposeTheme {
                ApiTest(isDataLoader)
                Column{
                    Button(onClick = {
                        var intent = Intent(this@MainActivity, AddExpense::class.java)
                        startActivity(intent)
                    },
                        modifier = Modifier.wrapContentHeight()) {
                        Text(text = "Add Expense")
                    }
                    if(isDataLoader.value){
                        LazyColumn{
                            items(dataList.value) {
                                mainUIListData(item = it)
                            }
                        }
                    }else{
                        CircularProgressIndicator()
                    }

                }
            }
        }
    }

    private fun ApiTest(isDataLoader: MutableState<Boolean>) {
        var apiEndPoint: ApiEndPoint
        var retrofit = ApiClient.getClient()
        apiEndPoint = retrofit.create(ApiEndPoint::class.java)
        apiEndPoint.getAllExpenses().enqueue(object : Callback<GetExpensesResponse> {
            override fun onResponse(
                call: Call<GetExpensesResponse>,
                response: Response<GetExpensesResponse>
            ) {
                if (response.body()?.status == 1) {
//                    Log.d("ApiTest", "DataList size: ${dataList.value.size}")
                    var tempData: List<Message> = response.body()!!.message
                    dataList.value.clear()
                    for (i in tempData){
                        dataList.value.add(i)
                    }
                    isDataLoader.value = true
                } else {
                    Log.e("ApiTest", "Error in API response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GetExpensesResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
            }

        })
    }

    @Composable
    fun mainUIListData(item:Message){
        var context = LocalContext.current
        Box(
            modifier = Modifier
                .padding(10.dp)
                .background(Color.Green)
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    Toast
                        .makeText(context, "Hi $item", Toast.LENGTH_SHORT)
                        .show()
                }
        ){
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .wrapContentHeight()
                ){
                    Text(text = item.itemName)
                    Text(text = item.price)
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .wrapContentHeight()
                ){
                    Button(onClick = { }) {
                        Text(text = "Update")
                    }
                    Button(onClick = { DeleteExpense(item.id) }) {
                        Text(text = "Delete")
                    }
                }
            }

        }
    }
    fun DeleteExpense(id: Int) {
        var apiEndPoint: ApiEndPoint
        var retrofit = ApiClient.getClient()
        apiEndPoint = retrofit.create(ApiEndPoint::class.java)
        apiEndPoint.DeleteExpense(id).enqueue(object : Callback<AddResponse> {
            override fun onResponse(call: Call<AddResponse>, response: Response<AddResponse>) {
                if (response.body()?.status == 1) {
                    recreate()
                } else {
//                    Log.e("ApiTest", "Error in API response: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<AddResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }

        })
    }
}





