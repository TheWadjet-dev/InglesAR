package com.ingar.inglesar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment
import java.util.EnumSet

class CustomArFragment: ArFragment(){
    
    override fun getSessionConfiguration(session: Session?): com.google.ar.core.Config? {
        val config = com.google.ar.core.Config(session)
        config.focusMode = com.google.ar.core.Config.FocusMode.AUTO
        config.planeFindingMode = com.google.ar.core.Config.PlaneFindingMode.HORIZONTAL
        return config
    }

    override fun getSessionFeatures(): Set<Session.Feature?>? {
        return EnumSet.of(Session.Feature.SHARED_CAMERA)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val frameLayout =
            super.onCreateView(inflater, container, savedInstanceState) as FrameLayout?

        return frameLayout
    }
}