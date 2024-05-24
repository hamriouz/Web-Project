import pika

def on_request(ch, method, properties, body):
    expression = body.decode()
    print(f" [x] Received request: {expression}")

    try:
        result = "hello from python"
    except Exception as e:
        result = "hello from python"

    ch.basic_publish(exchange='',
                     routing_key='response_queue',
                     body=result)
    print(f" [x] Sent response: {result}")

    ch.basic_ack(delivery_tag=method.delivery_tag)

rabbitmq_host = '72d4efe5-5145-4cce-83e0-7931d08bdf72.hsvc.ir'
rabbitmq_port = 31234
rabbitmq_user = 'rabbitmq'
rabbitmq_password = 'iti8hM6boEmj2XZiyATiUhhSJgKZoutb'

credentials = pika.PlainCredentials(rabbitmq_user, rabbitmq_password)

connection = pika.BlockingConnection(pika.ConnectionParameters(host=rabbitmq_host, port=rabbitmq_port, credentials=credentials))
channel = connection.channel()
channel.queue_declare(queue='request_queue', durable = True)
channel.queue_declare(queue='response_queue', durable = True)

channel.basic_qos(prefetch_count=1)
channel.basic_consume(queue='request_queue', on_message_callback=on_request)

print(" [x] Awaiting requests")
channel.start_consuming()
