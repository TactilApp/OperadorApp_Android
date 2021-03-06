
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

	- In-app Billing
	   OperadorApp usa el sistema In-App Billing de android para realizar el pago para poder disfrutar del servicio de acceso a la agenda de contactos.
	   Para configurarlo, hay que modificar en el fichero strings.xml la clave clave_ia_billing. Mientras que esta valga "ejemplo" el sistema quedará desactivado. 
	   Además, desde la consola de Google developer se deberá crear el producto con un id, que se colocará en el fichero strings.xml en la clave id_agenda_ia_billing.
	   Finalmente, se recomienda usar un payload para las compras, la palabra a usar deberá colocarse en el fichero strings.xml en la clave payload_agenda_ia_billing.
	   
Licencia
--------
[![](http://i.creativecommons.org/l/by-nc-sa/3.0/es/88x31.png)](http://creativecommons.org/licenses/by-nc-sa/3.0/es/deed.es_CO)

Operadorapp ha sido desarrollado por [TactilApp S.L.](http://tactilapp.com/) y se encuentra bajo una [Licencia Creative Commons Atribución-NoComercial-CompartirIgual 3.0 España](http://creativecommons.org/licenses/by-nc-sa/3.0/es/deed.es_CO).  
Basada en una obra en [https://github.com/TactilApp](https://github.com/TactilApp).