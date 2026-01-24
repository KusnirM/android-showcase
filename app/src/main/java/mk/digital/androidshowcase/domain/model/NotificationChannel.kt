package mk.digital.androidshowcase.domain.model

enum class NotificationChannel(
    val id: String,
    val channelName: String,
    val description: String
) {
    GENERAL(
        id = "001",
        channelName = "General",
        description = "General app notifications"
    ),
    REMINDERS(
        id = "002",
        channelName = "Reminders",
        description = "Calendar events and task reminders"
    ),
    PROMOTIONS(
        id = "003",
        channelName = "Promotions",
        description = "Deals, offers and updates"
    )
}
