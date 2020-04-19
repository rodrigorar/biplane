# Biplane

Biplane is meant to be a simple library that allows to create and use simple in memory caches in a java project.

## Usage

Has of right now, the only way to install Biplane in a project is to download the source code and install the project in your own local maven repository.
Dependencies needed:
  - Maven: 3.6.x
  - Java: 1.8

## Cache Types

Biplane currently supports two types of caches, the Simple Cache witch is a cache that is 100% controlled by the client, that means to all entries in the cache have to be explicitly managed by the client, except if a eviction policy is defined, then the cache will evict entries that match the eviction policy, but that is about when it comes to automatic entry management.
The Loading Cache is a little bit smarter, allowing the client to define a Cache Loader which in turn allows to load entries automaticly from a database or any other place the client desires. This cache can also have a defined eviction policy if so desired which will evict entries that are no longer valid. 

## Eviction Policy Types

Currently Biplane only supports one type of eviction policy, a time based one, meaning that an entry will live in the cache for a specific amount of time after its last known access.
