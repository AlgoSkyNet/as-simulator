# FAQ

**Installation & Configuration**

1. [Permission denied](#permissionDenied)
2. [Exception in thread "main" java.lang.NoClassDefFoundError](#classNotFound)
3. [Error loading as-common dynamic loadable library.](#dll)

**Metaspace Connection**

1. [SYS_ERROR (multicast_error - (8) grp_iface not a valid multicast interface)](#discovery)

-----------------

## Installation & Configuration

1. **Permission denied**
	{:#permissionDenied}
chmod +x as-simulator
2. **Exception in thread "main" java.lang.NoClassDefFoundError**
	{:#classNotFound}
The AS_HOME environment variable might not be set properly
3. **Error loading as-common dynamic loadable library.**
	{:#dll}
Check that the environment variable for your platform is correctly set:
* Windows: PATH
* Linux: LD_LIBRARY_PATH
* Mac OSX: DYLD_LIBRARY_PATH
* AIX: LIBPATH
* UX: SHLIB_PATH

Also check that you have the appropriate architecture installed (e.g. 32 vs 64-bit).

## Metaspace Connection

1. **SYS_ERROR (multicast_error - (8) grp_iface not a valid multicast interface)**
	{:#discovery}
Specify a discovery URL e.g. as-simulator -discovery tcp import ...