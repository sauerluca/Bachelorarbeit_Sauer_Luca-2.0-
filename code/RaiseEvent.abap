**********************************************************************
*   Generiere das Ereignis 'FERTIGUNGSAUFTRAG FREIGEGEBEN'
**********************************************************************
CALL METHOD CL_SWF_EVT_EVENT=>RAISE
  EXPORTING
    "Geschäftsobjekt-basiertes Ereignis
    IM_OBJCATEG = CL_SWF_EVT_EVENT=>MC_OBJCATEG_BOR
    "Geschäftsobjekt 'Fertigungsauftrag'
    IM_OBJTYPE  = 'BUS2005'
    "Beschreibung des Ereignisses
    IM_EVENT    = 'RELEASED'
    "Schlüsselattribut des Geschäftsobjekts
    IM_OBJKEY   = LV_OBJECT_KEY.