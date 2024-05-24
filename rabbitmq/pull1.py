import pika

def callback(ch, method, properties, body):
    response = body.decode()
    print(f" [x] Received response: {response}")
    ch.basic_ack(delivery_tag=method.delivery_tag)

def main():
    rabbitmq_host = '72d4efe5-5145-4cce-83e0-7931d08bdf72.hsvc.ir'
    rabbitmq_port = 31234
    rabbitmq_queue = 'response_queue'
    rabbitmq_user = 'rabbitmq'
    rabbitmq_password = 'iti8hM6boEmj2XZiyATiUhhSJgKZoutb'
    credentials = pika.PlainCredentials(rabbitmq_user, rabbitmq_password)

    connection = pika.BlockingConnection(pika.ConnectionParameters(host=rabbitmq_host, port=rabbitmq_port, credentials=credentials))
    channel = connection.channel()

    channel.queue_declare(queue='response_queue', durable=True)

    channel.basic_qos(prefetch_count=1)
    channel.basic_consume(queue='response_queue', on_message_callback=callback)

    print(" [*] Waiting for messages. To exit press CTRL+C")
    channel.start_consuming()

if __name__ == "__main__":
    main()
