package com.github.soon2beataco.soon2notnukefps.config.ui

import com.github.soon2beataco.soon2notnukefps.features.FeatureConfig

interface ToggleableFeature : FeatureConfig {
    var enabled: Boolean
}
