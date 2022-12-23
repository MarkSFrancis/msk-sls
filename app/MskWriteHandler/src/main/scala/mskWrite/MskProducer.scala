import org.apache.kafka.clients.producer._
import org.apache.kafka.clients.admin._
import scala.collection.JavaConverters._
import java.lang

object MskProducer {
  def sendMessage(topic: String, key: String, content: String) = {
    val producer = new KafkaProducer[String, String](this.getConfig)

    val record = new ProducerRecord[String, String](
      topic,
      key,
      content
    )

    producer.send(record)
  }

  def createTopic(topic: String): Unit = {
    val client = AdminClient.create(this.getConfig)

    val topics = client.listTopics.names.get
    if (topics.contains(topic)) {
      return
    }

    val kafkaTopic = new NewTopic(topic, 1, 3: Short)
    client.createTopics(Iterable { kafkaTopic }.asJavaCollection)
  }

  private def getConfig() = {
    val kafkaEndpoint = sys.env.getOrElse(
      "KAFKA_ENDPOINT",
      throw new Exception("KAFKA_ENDPOINT must be set")
    )

    Map[String, Object](
      "bootstrap.servers" -> kafkaEndpoint
    ).asJava
  }
}
