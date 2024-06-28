import pika

rabbitmq_host = '72d4efe5-5145-4cce-83e0-7931d08bdf72.hsvc.ir'
rabbitmq_port = 31234
rabbitmq_queue = 'request_queue'
rabbitmq_user = 'rabbitmq'
rabbitmq_password = 'iti8hM6boEmj2XZiyATiUhhSJgKZoutb'

credentials = pika.PlainCredentials(rabbitmq_user, rabbitmq_password)

connection = pika.BlockingConnection(pika.ConnectionParameters(host=rabbitmq_host, port=rabbitmq_port, credentials=credentials))
channel = connection.channel()

channel.queue_declare(queue=rabbitmq_queue, durable=True)

message = 'Hello, RabbitMQ!'

channel.basic_publish(
    exchange='',
    routing_key=rabbitmq_queue,
    body=message,
    properties=pika.BasicProperties(
        delivery_mode=2,
    ))

print(f" [x] Sent '{message}'")

connection.close()
