package com.ehsannarmani.textsearcher

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import com.ehsannarmani.textsearcher.ui.theme.TextSearcherTheme

const val longText =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a ipsum ligula. Praesent eu malesuada leo. Mauris aliquam pellentesque blandit. Nunc quis iaculis orci, vitae bibendum ante. Nulla lacinia enim ac consectetur imperdiet. Ut mauris lorem, fringilla ut pretium malesuada, commodo id neque. Suspendisse aliquam dui commodo, semper felis non, blandit ante. Proin aliquam congue lorem, ac iaculis mi. Morbi dolor felis, facilisis et urna vel, ultricies varius ipsum. Curabitur non lacus risus. Duis quis ultrices elit. Morbi elit ex, accumsan vel dolor quis, ornare auctor erat. Suspendisse vel libero sodales dolor semper commodo vel a metus. Sed suscipit mauris eu tellus faucibus, vitae venenatis magna maximus. Sed bibendum pulvinar gravida. Nam rhoncus vitae arcu in consectetur.\n" +
            "\n" +
            "Vestibulum fringilla mi et porttitor lobortis. Maecenas bibendum sit amet diam nec sollicitudin. Vivamus ultricies gravida erat, sagittis luctus mi sollicitudin eget. Nunc vestibulum leo sit amet sapien hendrerit, a ornare mi malesuada. In hac habitasse platea dictumst. Phasellus sem velit, rhoncus vitae tincidunt ac, lacinia id lectus. Phasellus nec nulla sem. Sed sem nulla, euismod in dictum sit amet, dapibus ac quam. Duis ut est laoreet, convallis nisi non, commodo elit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Suspendisse potenti. Duis gravida, metus ut vulputate hendrerit, augue nisl ultricies metus, auctor ornare dui mauris et nisi. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Cras et gravida sapien, nec ultricies tellus. Etiam ornare ante eu libero tincidunt hendrerit.\n" +
            "\n" +
            "Fusce ut nibh in nunc pulvinar lobortis. Pellentesque diam massa, tincidunt quis mollis et, semper in ante. Nunc nec scelerisque ligula, at volutpat ipsum. Proin in nulla sagittis, fringilla lorem id, iaculis magna. Suspendisse maximus odio nulla, id luctus nisl mollis quis. Donec egestas suscipit rutrum. Praesent volutpat nulla a eleifend imperdiet. Aliquam erat volutpat. Praesent tincidunt, ex eu pharetra dignissim, velit metus tempus ipsum, in feugiat lorem risus non sem. Nullam felis lacus, suscipit sed tincidunt eget, sollicitudin eget elit. Curabitur hendrerit vitae eros nec iaculis. Donec dapibus mollis ante, vel gravida erat lacinia eu. Ut eget elementum nunc. Etiam eleifend mauris libero, vel volutpat diam dapibus eget. Phasellus ut est quis nunc finibus convallis a vitae lorem.\n" +
            "\n" +
            "Quisque aliquam vulputate tortor id lacinia. Vivamus tincidunt quam arcu, nec aliquet risus maximus id. Cras nec suscipit ligula. Vestibulum molestie enim vestibulum eros dignissim, tempor feugiat orci venenatis. Integer id aliquet nulla. Nullam scelerisque augue augue, id dignissim felis efficitur eu. Interdum et malesuada fames ac ante ipsum primis in faucibus. Curabitur nec neque at massa feugiat scelerisque. Aenean luctus magna quis placerat vulputate. Etiam fringilla, ex id lacinia interdum, felis felis aliquam risus, a ultrices purus massa sed nisi. Sed pretium eu nunc eu ullamcorper. Vivamus vitae dignissim magna, ut euismod orci. Nam sit amet rhoncus arcu.\n" +
            "\n" +
            "Pellentesque sodales scelerisque ultricies. Nunc urna urna, venenatis ut sodales nec, pharetra vitae turpis. Curabitur a facilisis ante. Nam porttitor dolor pellentesque tincidunt congue. Maecenas efficitur ex sed molestie varius. Nulla vel ornare est, sed euismod nisl. In eget lorem nisi. Pellentesque sodales viverra odio et interdum. Etiam turpis arcu, volutpat ac dapibus vulputate, venenatis vitae nibh. Sed bibendum quam ac iaculis imperdiet. Donec ultrices malesuada ex, non sodales velit vehicula quis. Mauris justo magna, tincidunt venenatis erat vel, varius aliquet mi. Pellentesque pulvinar luctus est, vitae pharetra lorem scelerisque in. Donec dapibus enim ac nulla rhoncus, vestibulum blandit eros commodo. Donec molestie arcu et ultricies mollis.\n" +
            "\n" +
            "Nulla vel vulputate urna. Maecenas tempor quam risus, eu porttitor elit rhoncus ac. Cras facilisis felis non erat porta lobortis. Aliquam urna sem, fringilla vel risus quis, facilisis vehicula risus. Etiam lacinia mi mauris, non sollicitudin ante molestie eu. Nullam rutrum lectus id vulputate eleifend. Maecenas fringilla hendrerit eleifend. Pellentesque vel mauris a est maximus sagittis ac quis orci. Vivamus erat odio, bibendum et massa quis, posuere dictum erat. Maecenas dapibus mollis dignissim. Phasellus ut tortor ut felis lobortis consectetur nec eu dui. Integer ligula libero, pharetra nec semper et, feugiat id diam. Aliquam quis mauris sed dolor eleifend vestibulum. Integer nec feugiat erat. Ut ultricies varius metus, eu ultrices ex.\n" +
            "\n" +
            "Fusce dignissim faucibus ultrices. Aenean pellentesque turpis eget orci pulvinar varius. Phasellus sed auctor massa, ac tristique neque. Pellentesque molestie porttitor cursus. Praesent quis tempor metus. Cras bibendum massa augue, in dictum nunc vestibulum non. Sed at ipsum sollicitudin, scelerisque arcu vel, malesuada libero. Duis efficitur fringilla orci, et pellentesque massa tincidunt vitae. Morbi leo massa, elementum a imperdiet in, fringilla in arcu. Quisque magna neque, commodo non enim quis, commodo aliquam nulla. Quisque a lorem a nunc molestie faucibus. Praesent ultricies enim eu est rhoncus, ac aliquet orci tempor. Duis mattis augue non quam pellentesque, sed bibendum ex dictum. Sed elit nunc, malesuada vel justo eget, tempor accumsan turpis. Quisque a lectus sollicitudin, egestas lorem eget, blandit ligula.\n" +
            "\n" +
            "Phasellus aliquam, tortor eu fringilla viverra, enim leo mattis est, quis dignissim ante eros sed diam. Praesent turpis ligula, varius vel hendrerit a, interdum dignissim felis. Morbi eu tincidunt mi, vel ultricies lacus. Ut id justo eget purus venenatis faucibus. Duis laoreet finibus condimentum. Morbi maximus a ante eget ultrices. Fusce neque sapien, tristique vel vehicula id, aliquet vitae augue. Fusce ut scelerisque lacus. In ultrices tincidunt velit, ac ullamcorper nisl commodo at. Fusce molestie elementum dolor, at congue mi cursus sit amet. Nulla facilisi.\n" +
            "\n" +
            "Nunc in sem quis metus venenatis scelerisque. Suspendisse varius nulla eu consequat tincidunt. Morbi elementum quis ex et tempor. Fusce rutrum faucibus congue. Vivamus tempus iaculis orci non molestie. Maecenas vitae nunc eros. Sed porttitor facilisis vestibulum. Duis quis ullamcorper ex. Curabitur suscipit nisl ac tincidunt pulvinar. Praesent blandit condimentum elit, rhoncus ornare massa pellentesque vitae. Donec tempor molestie urna vitae hendrerit. Maecenas eros urna, efficitur vitae feugiat at, tempor non mauris.\n" +
            "\n" +
            "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur a urna tempor, posuere nunc semper, semper magna. Praesent sit amet semper dui, ut semper lorem. Curabitur neque velit, rhoncus a felis eget, accumsan dapibus quam. Nunc augue quam, hendrerit at ullamcorper non, pulvinar ac quam. Nunc hendrerit mi faucibus tincidunt aliquet. Pellentesque accumsan dignissim lectus et commodo. Aenean iaculis arcu at eleifend semper. Nam sit amet lectus imperdiet, tincidunt lacus quis, aliquet ipsum. Proin sed egestas magna. In auctor ante a risus placerat, eu rutrum felis consectetur.\n" +
            "\n" +
            "Nunc feugiat lorem metus. Mauris mi velit, vulputate in fermentum vitae, tempor eget augue. Mauris ac hendrerit turpis. Fusce facilisis, risus in pharetra placerat, ipsum purus elementum velit, quis vestibulum lorem orci interdum urna. Curabitur accumsan, odio sed tristique porta, libero odio aliquam justo, ac tincidunt magna leo ac sapien. Fusce euismod interdum eleifend. Aenean ac metus ligula.\n" +
            "\n" +
            "Phasellus mattis odio vitae rhoncus tempus. Nullam aliquam, turpis nec malesuada pulvinar, urna ex viverra ligula, sit amet dignissim urna turpis nec justo. Nunc iaculis urna nec commodo dignissim. Nam placerat, mi in molestie egestas, nulla felis pharetra erat, eu tempor erat erat vitae diam. Etiam sodales vitae risus sit amet ornare. Phasellus consectetur ante ac enim luctus lobortis. Nunc libero arcu, posuere sit amet viverra quis, ultrices sit amet libero. Donec non erat vel nibh suscipit ornare ac vel est. Maecenas interdum leo ut hendrerit fringilla.\n" +
            "\n" +
            "Aenean sed tristique dui. Sed vel dictum nisl. Pellentesque rhoncus nisl rhoncus, ultricies felis ac, vestibulum felis. Aliquam auctor, ipsum ut scelerisque pretium, massa tortor volutpat mi, ut lobortis ex nunc eget ex. Suspendisse tincidunt, nunc eu interdum eleifend, elit nulla luctus ante, eu venenatis lorem sapien non ipsum. Donec aliquet, sapien id pulvinar tincidunt, mi risus elementum libero, at finibus sapien ex eget dolor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Vestibulum eget dignissim augue, a tempor augue.\n" +
            "\n" +
            "Mauris bibendum, risus at malesuada ornare, eros dui condimentum sapien, sit amet facilisis metus erat et dui. Curabitur eu purus non purus fermentum commodo. Nulla eget dapibus quam. Quisque a risus pellentesque, rutrum arcu ut, pulvinar leo. Donec neque leo, facilisis vitae arcu sed, ultricies varius nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Proin tincidunt id felis venenatis varius. Vivamus sodales non ligula et eleifend. Vivamus ultrices, augue vitae semper scelerisque, purus nisi eleifend nulla, id mattis mauris orci quis erat. Mauris eu dapibus leo, eget sagittis lectus. Aenean condimentum placerat mauris et ultrices. Vivamus non dolor odio.\n" +
            "\n" +
            "Praesent sagittis dui eget nulla dapibus tempus. Mauris porta feugiat odio, non semper est pharetra sit amet. Sed imperdiet lobortis dapibus. Suspendisse sagittis ligula sit amet ultricies commodo. Proin consequat varius leo. Quisque maximus tortor eu turpis laoreet, eu tincidunt nibh tempus. Mauris convallis turpis ut massa convallis, ac interdum mi posuere. Nullam suscipit, leo vitae aliquet vehicula, nisl libero hendrerit nulla, nec aliquet lectus neque efficitur justo. In hac habitasse platea dictumst. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Proin varius est massa, sit amet commodo sapien facilisis nec. Quisque aliquam leo sit amet aliquet finibus. Nunc et purus quis nunc aliquet facilisis. Suspendisse viverra imperdiet pretium. Donec bibendum enim enim, et suscipit eros ultrices ut.\n" +
            "\n" +
            "Phasellus justo dolor, finibus et dapibus vitae, luctus in erat. Phasellus tortor leo, egestas id elit sed, viverra pulvinar neque. Morbi nec neque non enim dignissim gravida nec at erat. Phasellus eu bibendum diam. Sed id est faucibus quam rutrum tempor rutrum ut erat. Nulla efficitur, ipsum a consectetur sodales, nunc mi accumsan odio, non ullamcorper nulla neque sed turpis. Sed nec pretium ante, eget aliquam erat. Fusce id felis in neque bibendum tristique. Sed orci felis, consequat vel tincidunt non, tincidunt quis nisl.\n" +
            "\n" +
            "Praesent et magna lacus. Phasellus eu sem at tortor commodo malesuada. Sed non ligula luctus, tempus mauris sed, pellentesque justo. Praesent eu ex et sapien lobortis fermentum. Vestibulum non viverra dolor. Cras aliquam porttitor nibh. Pellentesque elementum venenatis ullamcorper. Aliquam vitae accumsan tellus. Donec eros orci, mollis id tincidunt ut, ornare vel est. Aenean eleifend fermentum ex nec viverra. Proin commodo nisl vel euismod sodales. Fusce nec dictum purus. Curabitur leo lorem, mattis eget dictum nec, rutrum a ex. Vivamus mollis, orci a consequat mollis, enim mi aliquet augue, condimentum mollis neque lorem et felis.\n" +
            "\n" +
            "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam pulvinar venenatis elit elementum lacinia. Etiam sed posuere dolor, id tempor libero. Donec dapibus quam gravida bibendum pharetra. Etiam ac purus in eros maximus rhoncus a euismod eros. Nam pretium velit at fermentum ornare. Suspendisse iaculis pharetra turpis, vitae cursus ex pulvinar eu. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dapibus aliquam mi, vitae posuere risus placerat sed. Vivamus bibendum nulla quis tellus porta, id eleifend enim interdum. Vivamus bibendum metus euismod lobortis iaculis. Nulla facilisi.\n" +
            "\n" +
            "Praesent eu lacus accumsan, molestie nunc sit amet, scelerisque nunc. Nunc fringilla eu nisi ut dapibus. Ut lacinia molestie libero elementum venenatis. Pellentesque sit amet finibus tortor, a laoreet ipsum. Maecenas mollis rhoncus felis, at posuere lacus sollicitudin vitae. Maecenas eu arcu rhoncus velit vulputate mollis vel et tellus. Donec mattis lobortis enim quis faucibus. Mauris id massa nibh.\n" +
            "\n" +
            "Duis dignissim, dui in interdum faucibus, diam eros ornare purus, eget finibus dui mi ac tellus. Aliquam eget aliquet libero, nec fermentum diam. Suspendisse non nibh porta, pulvinar dolor non, maximus tellus. Vivamus eget purus erat. Nullam nec ante vitae quam egestas ultrices egestas in arcu. Suspendisse vitae tincidunt mi. Duis rhoncus vestibulum metus at volutpat. Praesent eleifend tellus arcu, vel rutrum libero congue laoreet. Sed quis imperdiet nibh. Aliquam congue purus dui, eu ornare neque pellentesque eget. Pellentesque nec tellus vulputate, tincidunt quam eu, vestibulum neque. In hac habitasse platea dictumst. Ut nunc mauris, ultrices eu auctor quis, auctor eu augue. Sed varius pellentesque nisi, sed suscipit erat posuere in. Pellentesque sodales vitae dui vel auctor. Sed felis nunc, tincidunt ut sem ut, ullamcorper semper leo."

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TextSearcherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val query = remember {
                        mutableStateOf("")
                    }
                    val searcher = rememberTextSearcher(text = longText)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(text = "${searcher.searchResults.count()} searches reached until now")
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(value = query.value, onValueChange = {
                                query.value = it
                            }, modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(onClick = {
                                searcher.search(
                                    query = query.value,
                                    queryStyle = SpanStyle(
                                        color = Color(0xFF4CAF50)
                                    ),
                                    rectOptions = RectOptions(
                                        color = Color.Green,
                                        padding = PaddingValues(horizontal = 4.dp)
                                    )
                                ).also {
                                    Toast.makeText(this@MainActivity, "Results: ${it.resultsFound}", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { searcher.previous() },modifier=Modifier.weight(1f),shape = RoundedCornerShape(8.dp)) {
                                Text(text = "Previous")
                            }
                            Button(onClick = { searcher.next() },modifier=Modifier.weight(1f),shape = RoundedCornerShape(8.dp)) {
                                Text(text = "Next")
                            }
                        }
                        Text(
                            text = searcher.annotatedText,
                            modifier = Modifier.searcher(searcher),
                            onTextLayout = searcher::handleTextLayout
                        )
                    }

                }
            }
        }
    }
}


