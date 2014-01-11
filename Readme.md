DetectCME 
===========

This is a simple debug plugin that attempts to detect when other plugins overstep their 
bounds and call Bukkit API asynchronously. This often randomly triggers a 
ConcurrentModificationException at a different spot, making debugging difficult.

The plugin will detect illegal calls to the following locations:
* All list and set fields in WorldServer and World.
* The list of tracked entities in EntityTracker.
* The list of scoreboards in CraftScoreboardManager.