DetectCME 
===========

This is a simple debug plugin that attempts to detect when other plugins overstep their 
bounds and call Bukkit API asynchronously. This often triggers (but randomly) causes a 
ConcurrentModificationException at a different spot, making debugging difficult.