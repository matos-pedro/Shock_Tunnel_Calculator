```
---
title: 'Shock Tunnel Calculator'
authors:
-name: Pedro Matos
    equal-contrib: true
    affiliation: 1
-name: Leda Vialta
    equal-contrib: true
    affiliation: 1
-name: Dermeval Carinhana
    equal-contrib: true
    affiliation: 1

affiliations:
-name: Instituto de Estudos Avançados, DCTA, Brazil
   index: 1
date: 25 March 2025
bibliography: paper.bib

# Optional fields if submitting to a AAS journal too, see this blog post:
# https://blog.joss.theoj.org/2018/12/a-new-collaboration-with-aas-publishing
aas-doi: 10.3847/xxxxx <- update this with the DOI from AAS once you know it.
aas-journal: Astrophysical Journal <- The name of the AAS journal.
---
```


# SYMBOL LIST

- **a** - Speed of sound
- **g** - Gain factor
- **h** - Specific enthalpy
- **M** - Mach number
- **p** - Pressure
- **r** - Mass density
- **s** - Specific entropy
- **T** - Temperature
- **u** - Velocity
- **α** - Adiabatic expansion coefficient

### Subscripts:

- **e** - Free flow
- **p** - Post-shock condition
- **r** - Reflected shock wave
- **s** - Primary shock wave
- **th** - Nozzle throat
- **0** - Reservoir condition
- **1** - Initial condition of the air, the test gas
- **2** - Shocked condition
- **4** - Initial condition of the driver gas
- **5** - Reflected condition

# Summary

Shock tubes and shock tunnels are experimental facilities used to study high-speed gas dynamics, simulating extreme flow conditions relevant to aerospace, defense, and fundamental fluid mechanics research. Characterizing these facilities is a critical step in high-speed flow studies, yet traditional methods often require complex calculations and specialized software. This project introduces a mobile application that streamlines this process, providing a simplified, fast, user-friendly solution for researchers, students, and engineers. By enabling real-time characterization directly on a mobile device, the app enhances accessibility and accelerates experimental diagnostics. Its intuitive interface and instant results facilitate rapid decision-making, making it a valuable tool for both academic and applied research in aerodynamics.

# Statement of need

Shock Tube and Tunnel experiments, essential in the study of supersonic flows, require accurate calculations of shock wave conditions and free stream properties. Traditional methods for characterizing these experiments often rely on idealized assumptions based on the ideal gas law, which may not be suitable for real gas behavior in high-pressure, high-temperature conditions. These methods, while potentially fast, are limited in their ability to model real gas effects accurately and provide comprehensive results for more complex scenarios. Additionally, although some approaches exist that model real gas behavior, they typically rely on older computational codes, such as one from 1993[@minucci93], which are not intuitive and still require access to a computer, limiting their accessibility and ease of use in practical applications.

The Shock Tube and Tunnel Calculator addresses this issue by providing a mobile, user-friendly tool that enables rapid calculation of the necessary parameters for characterizing shock tube and tunnel experiments. The app focuses on real gas behavior, using equilibrium chemical transitions to estimate shock and reflected conditions based on the initial pressure, temperature, and shock wave speed. Additionally, when a nozzle is used, it estimates the free stream based on the throat-to-exit area ratio or measured Pitot pressure, assuming isentropic, non-reactive flow. This provides a more accurate solution for real gas scenarios, making it a practical tool for quick diagnostics and offering greater accessibility in various experimental settings.



# Mathematics

## Shock Tube

For simplicity, the methodology used neglects mass, momentum, or energy transfer between the walls of the shock tunnel and the air filling it. Thus, the changes in states predicted for the test gas are not explained by viscous, frictional, or conductive phenomena, and any evolution is justified by the interaction between the test gas, shock waves (typical in shock tubes), and isentropic expansions (common in nozzles).

### Shocked Condition

From the conservation equations of momentum, energy, and mass in the one-dimensional, steady, constant-area form for gas processed by a normal shock wave, defined as the Rankine-Hugoniot relations, the following relationships are obtained:

$$
p_2 = p_1 + r_1 u_s^2 \left( 1 - \frac{r_1}{r_2} \right) \tag{1}
$$


$$
h_2 = h_1 + \frac{u_s^2}{2} \left[ 1 - \left( \frac{r_1}{r_2} \right)^2 \right] \tag{2}
$$


$$
u_2 = u_s \left( 1 - \frac{r_1}{r_2} \right) \tag{3}
$$


$$
r_2 = r_2(h_2, p_2) \tag{4}
$$
