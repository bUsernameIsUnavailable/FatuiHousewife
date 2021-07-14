import os
from dotenv import load_dotenv

import discord

client = discord.Client()
trigger_words = [
    "baby",
    "fatui",
    "housewife",
    "mpreg",
    "child",
    "tartaglia",
    "ajax"
]


@client.event
async def on_ready():
    print(f"Logged in as {client.user}")


@client.event
async def on_message(message):
    if message.author == client.user:
        return

    if any(word in "".join(message.content.split()).lower() for word in trigger_words):
        await message.channel.send(file=discord.File('childempreg.jpg'))


load_dotenv()
client.run(os.environ['TOKEN'])
