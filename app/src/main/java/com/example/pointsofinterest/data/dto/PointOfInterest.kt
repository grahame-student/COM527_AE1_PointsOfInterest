import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points_of_interest")

data class PointOfInterest(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    var type: String,
    var country: String,
    var region: String,
    var lon: Double,
    var lat: Double,
    var description: String
)
