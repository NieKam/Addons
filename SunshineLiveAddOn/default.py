import simplejson
import os
import sys
import urllib
import urlparse
import requests
import xbmcaddon
import xbmcgui
import xbmcplugin

ptv = xbmcaddon.Addon()
BASE_RESOURCE_PATH = os.path.join(ptv.getAddonInfo('path'), "resources")
sys.path.append(os.path.join(BASE_RESOURCE_PATH, "lib"))
jsonData = os.path.join(ptv.getAddonInfo('path'), "resources") + os.path.sep + "radios.json"
fanart = os.path.join(ptv.getAddonInfo('path'), 'fanart.jpg')

def build_url(query):
    base_url = sys.argv[0]
    return base_url + '?' + urllib.urlencode(query)

def get_thumbnail_url(base, thumbnail):
    return base + thumbnail + ".jpg"

def get_metadata(url):
    info = {}
    data = simplejson.loads(requests.get(url).text)
    if 'title' in data:
        info['title'] = data['title']
    if 'artist' in data:
        info['artist'] = data['artist']
    
    return info
        
def build_song_list():
    song_list = []
    json_data = open(jsonData, "r")
    data = simplejson.loads(json_data.read())
    json_data.close()
    thumbnail_url = data['thumbnail_url']
    metadata_url = data['metadata_url']
    for song in data["songs"]:
        info = get_metadata(metadata_url + song['metadata'])
        
        li = xbmcgui.ListItem(label=song['name'], thumbnailImage=get_thumbnail_url(thumbnail_url, song['thumbnail']))
        li.setProperty('IsPlayable', 'true')
        li.setProperty('fanart_image', fanart)
        li.setInfo('music', info)
        url = build_url({'mode': 'stream', 'url': song['url'], 'title': song['name']})
        # add the current list item to a list
        song_list.append((url, li, False))
    
    xbmcplugin.addDirectoryItems(addon_handle, song_list, len(song_list))
    # set the content of the directory
    xbmcplugin.setContent(addon_handle, 'songs')
    xbmcplugin.endOfDirectory(addon_handle)
    
def play_song(url):
    # set the path of the song to a list item
    play_item = xbmcgui.ListItem(path=url)
    # the list item is ready to be played by Kodi
    xbmcplugin.setResolvedUrl(addon_handle, True, listitem=play_item)
    
def main():
    args = urlparse.parse_qs(sys.argv[2][1:])
    mode = args.get('mode', None)
    # initial launch of add-on
    if mode is None:
        # display the list of songs in Kodi
        build_song_list()
    # a song from the list has been selected
    elif mode[0] == 'stream':
        # pass the url of the song to play_song
        play_song(args['url'][0])
    
if __name__ == '__main__':
    addon_handle = int(sys.argv[1])
    main()