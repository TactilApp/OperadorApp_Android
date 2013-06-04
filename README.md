
![](https://github.com/TactilApp/OperadorApp/raw/master/Recursos/00%20-%20icono%20y%20default/Icon%402x.png) OperadorApp
=======================
OperadorApp sirve para descubrir de qué compañía es un número de teléfono móvil (de momento únicamente en España).

Se puede descargar gratuítamente desde Google Play: [OperadorApp](http://play.google.com/store/apps/details?id=com.tactilapp.operadorapp).

Instalación
===========
A continuación se explican los pasos que necesitas para probar la aplicación en tu propio ordenador.

Clonado
-------
	git clone git@github.com:TactilApp/OperadorApp_android.git
	
Configuración
-------------
Por último, la aplicación emplea UrbanAirship para notificaciones, Flurry para estadísticas y Admob para publicidad.

    - Urban Airship
	   Para configurarlo, hay que modificar el fichero assets/airshipconfig.properties con las claves que te sean asignadas. Mientras que estas valgan "ejemplo" el sistema de notificaciones quedará desactivado.

    - Flurry
	   Para configurarlo, hay que modificar en el fichero strings.xml la clave flurry_id. Mientras que esta valga "ejemplo" el sistema quedará desactivado.

	- Admob
	   Para configurarlo, hay que modificar en el fichero strings.xml la clave admob_unit_id. Mientras que esta valga "ejemplo" el sistema quedará desactivado.