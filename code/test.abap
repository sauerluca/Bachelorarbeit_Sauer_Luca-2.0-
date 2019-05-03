 * Instantiate an empty event container
   CALL METHOD cl_swf_evt_event=>get_event_container
     EXPORTING
       im_objcateg  = cl_swf_evt_event=>mc_objcateg_cl    "Class-based event
       im_objtype   = 'ZCL_JCN_WFOBJ'                     "name of event class
       im_event     = 'CREATED'                           "name of event
     RECEIVING
       re_reference = lr_event_parameters.
 *  Set up the name/value pair to be added to the container
 *  and ADD the name/value pair to the event conainer
   TRY.
       CALL METHOD lr_event_parameters->set
         EXPORTING
           name  = 'MY_ORG_PARAMETER_TEST'
           value = 'VALUE_1_Test'.
 *         UNIT =            "if values with units are passed
 *       IMPORTING
 *         RETURNCODE = ...  "in production this should be checked
     CATCH cx_root.
       WRITE: / 'Error add parameter'.
   ENDTRY.

   TRY.
 * Raise the event and pass the container
       CALL METHOD cl_swf_evt_event=>raise
         EXPORTING
           im_objcateg        = cl_swf_evt_event=>mc_objcateg_cl
           im_objtype         = 'ZCL_JCN_WFOBJ'
           im_event           = 'CREATED'
           im_objkey          = lv_object_key
           im_event_container = lr_event_parameters.
     CATCH cx_root.
       WRITE: / 'Error'.
   ENDTRY.