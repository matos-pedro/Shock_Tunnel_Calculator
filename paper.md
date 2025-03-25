---
title: 'STT_Calc, an Android app for shock tunnel characterization'
tags:
  - Shock Tunnel
  - Supersonics
authors:
  - name: Pedro Matos
    affiliation: 1
  - name: Leda Marise
    affiliation: 1
  - name: Dermeval Carinhanda
    affiliation: 1
affiliations:
 - name: Instituto de Estudos Avançados, DCTA, Brazil
   index: 1
repository: https://github.com/matos-pedro/Shock_Tunnel_Calculator
date: 25 March 2025
bibliography: paper.bib
---
# Summary

Shock tubes and shock tunnels are experimental facilities used to study high-speed gas dynamics, simulating extreme flow conditions relevant to aerospace, defense, and fundamental fluid mechanics research. Characterizing these facilities is a critical step in high-speed flow studies, yet traditional methods often require complex calculations and specialized software. This project introduces a mobile application that streamlines this process, providing a simplified, fast, user-friendly solution for researchers, students, and engineers. By enabling real-time characterization directly on a mobile device, the app enhances accessibility and accelerates experimental diagnostics. Its intuitive interface and instant results facilitate rapid decision-making, making it a valuable tool for both academic and applied research in aerodynamics.

# Statement of need

Shock Tube and Tunnel experiments, essential in the study of supersonic flows, require accurate calculations of shock wave conditions and free stream properties. Traditional methods for characterizing these experiments often rely on idealized assumptions based on the ideal gas law, which may not be suitable for real gas behavior in high-pressure, high-temperature conditions. These methods, while potentially fast, are limited in their ability to model real gas effects accurately and provide comprehensive results for more complex scenarios. Additionally, although some approaches exist that model real gas behavior, they typically rely on older computational codes, such as one from 1993[@minucci93], which are not usually intuitive and still require access to a computer, limiting their accessibility and ease of use in practical applications.

The Shock Tube and Tunnel Calculator addresses this issue by providing a mobile, user-friendly tool that enables fast calculation of the necessary parameters for characterizing shock tube and tunnel experiments. The app focuses on real air equilibrium parameters to estimate shocked and reflected conditions based on the initial pressure, temperature, and shock wave speed. Additionally, when a nozzle is used, it estimates the free stream based on the throat-to-exit area ratio or measured Pitot pressure, assuming isentropic, non-reactive flow. This provides an accurate solution for real gas scenarios, making it a practical tool for quick diagnostics and offering greater accessibility in various experimental settings.

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

Where the subscript **s** refers to the primary shock wave, and the subscripts **1** and **2** refer to the initial and shocked conditions of the test gas. The equalities are arranged to adopt the shock wave as the inertial reference and express the dependence of the conservation equations on the density ratio between the initial and shocked states.

Although the initial condition of the test gas is known, along with the velocity of the incident shock wave (a commonly measured parameter), the lack of information about the shocked gas prevents direct application of the above equations, so an iterative approach is adopted.

Initially, the density of the shocked region is guessed, allowing the calculation of **p2** and **h2**. Then, the density **r2** is calculated using an equation of state that relates density, pressure, and specific enthalpy. Once a new density is defined, the parameters **p2** and **h2** are recalculated, allowing the density to be recalculated again. This procedure is repeated until the convergence criterion is met. Finally, the velocity **u2** is calculated. As a direct consequence of using an equation of state, the procedure assumes that the air (test gas) evolves between equilibrium states.

All the equations of state used in the app are based on the work of [@srini87], in which curve fitting was defined to calculate the thermodynamic properties of real air in equilibrium at temperatures up to 20,000 K. These curve fits can be found transcribed in Java in [@java2025], the code used and adapted in the development of the app.


### Reflected Condition

The reflected condition is calculated in a manner similar to the shocked condition; however, it differs in boundary condition: the reflected shock wave must have such magnitude that it brings the test gas to rest under the shocked condition, i.e., **u5 = 0**. Although this is a typical condition for shock tubes, this consideration is particularly valid in shock tunnels where the area restriction caused by the nozzle exceeds about 90% of the area of the cross-section and end of the tube. Thus, the conservation equations used to calculate the reflected condition are:

$$
u_r = \frac{u_2}{\left(\frac{r_5}{r_2} - 1\right)} \tag{5}
$$

$$
p_5 = p_2 + r_2 \left( u_2 + u_r \right)^2 \left( 1 - \frac{r_2}{r_5} \right) \tag{6}
$$

$$
h_5 = h_2 + \frac{p_5 - p_2}{2r_2} \left( 1 + \frac{r_2}{r_5} \right) \tag{7}
$$

$$
r_5 = r_5(p_5, h_5) \tag{8}
$$


Where the symbol **ur** and subscript **5** refer to the velocity of the reflected shock wave and the reflected condition.

Similarly to the previous step, a value for the density **r5** is guessed, allowing the calculation of the reflected shock velocity, pressure, and enthalpy. The density **r5** is then recalculated using an equation of state (equation 8) so that **p5** and **h5** can be recalculated. This procedure is performed until the convergence criterion is reached, at which point the reflected condition is specified.


### Reservoir Condition

The reservoir condition is included in the app to cover experiments performed under the under-tailored/over-tailored conditions, cases where successive expansion/compression waves, resulting from the interaction between the reflected shock wave and the contact surface, reach the test gas after the establishment of the reflected condition. While they cause entropy increases, the shock waves that occur in the over-tailored case can be approximated as isentropic processes [@copper1961], simplifying the estimation of the reservoir condition. Expansion waves, resulting from the under-tailored case, are naturally isentropic.

Thus, knowing the specific entropy **s5** of the reflected condition and providing the reservoir pressure **p0**, the reservoir conditions are calculated using the equations of state from [@srini87]. In the absence of a measurement for the reservoir pressure, the app assumes its value is equal to that calculated for the reflected condition: **p0 = p5**.



## Nozzle-Exit Flow

The free flow will be calculated and displayed if at least one of the following parameters is entered by the user: the nozzle area ratio, represented by **A/A***, or an experimental measurement of the Pitot pressure, denoted as **pt**. To do this, it is assumed that the flow evolves from the reservoir condition to the nozzle exit in an adiabatic and non-reactive manner.

In the first case, when the area ratio **A/A*** is known, the Mach number of the free flow **Me** is found using the following equation:

$$
\left(\frac{A}{A^*}\right)^2 = \frac{1}{M_e^2} \left[\frac{2}{\gamma_0 + 1} \left( 1 + \frac{\gamma_0 - 1}{2}M_e^2 \right)\right]^{\frac{\gamma_0 + 1}{\gamma_0 - 1}}
$$

From the Mach number **Me** and the reservoir condition, the free flow can be calculated using the following isentropic relations:

$$
T_e = T_0 \left[ 1 + \frac{M_e^2 (\gamma_0 - 1)}{2} \right] \tag{10}
$$

$$
p_e = p_0 \left[ 1 + \frac{M_e^2 (\gamma_0 - 1)}{2} \right]^{\frac{-\gamma_0}{\gamma_0 - 1}} \tag{11}
$$

$$
r_e = r_0 \left[ 1 + \frac{M_e^2 (\gamma_0 - 1)}{2} \right]^{\frac{-1}{\gamma_0 - 1}} \tag{12}
$$

The app also provides an estimate for the Pitot pressure. To do so, it is assumed that a normal shock wave will form in front of a Pitot sensor, bringing the free flow to a new condition, represented in the app by the subscript **p**. This condition is calculated using equations 1 and 2 from section 3.1, assuming a velocity of $M_e \sqrt{\gamma_0 p_e/r_e}$ for the free flow. The velocity **up**, in turn, is calculated using the relation $u_e(r_e/r_p)$ . The conditions for the Pitot, with subscript **t** in the app, are finally calculated by assuming that the "post-shock" flow reaches stagnation in an isentropic manner, using relations identical to equations 8, 9, and 10.

In the second case, when the Pitot pressure is used as input, the app employs the secant method to identify the area ratio whose corresponding Pitot pressure matches the entered value. This area ratio is then shown in the output log as **A/A***. Once the equivalent area ratio is identified, the numerical procedure follows the same steps as when the area ratio is known.

Given the nature of the methodology adopted, three parameters are required for the basic use of the app: the initial pressure and temperature of the test gas, **p1** and **t1**, and the velocity of the incident shock wave, **us**. The 'Experimental Data' and 'Analytical Ms' tabs differ solely in how the velocity **us** is identified and will be explained later. By entering the area ratio **A/A*** and Pitot pressure **pp**, the free flow will be calculated independently for both cases and shown in the output log.

## Determination of **us**, the incident shock wave velocity

All the calculations presented here are based on the initial condition of the test gas and the velocity of the incident shock wave, a commonly measured parameter in shock tube experiments. Thus, the 'Experimental Data' tab has fields for entering the initial pressure and temperature of the air (in kilopascals, kPa, and Kelvin, K), as well as an entry for the incident shock wave velocity, which should be entered in meters per second (m/s). Pressing the [GO!] button will trigger the procedure outlined in sections 1.1 and 1.2, and a new window will open, providing an output log with the results obtained.

The 'Analytical Ms' tab, on the other hand, aims to estimate the primary shock wave velocity **us**, after which the entire procedure previously shown will follow. First, the Mach number **Ms** is estimated using the following relation:


$$
\frac{p_4}{p_1} g = \left[ 1 + \frac{2\gamma_1}{\gamma_1 + 1} (M_s^2 - 1) \right] \left[ 1 - \frac{\gamma_4 - 1}{\gamma_1 + 1} \frac{a_1}{a_4} g^{\frac{1 - \gamma_4}{2\gamma_4}} \left( \frac{M_s - 1}{M_s} \right) \right]^{-\frac{2\gamma_4}{\gamma_4 - 1}} \tag{13}
$$


This equation differs from the standard shock tube equation due to the inclusion of the parameter **g**, an empirical gain factor added to account for devices where the high and low-pressure sections have distinct cross-sectional areas [@resler52]. Due to simplifications, such as assuming that diaphragm rupture is instantaneous, the Mach number **Ms** calculated will be lower than that obtained experimentally. For this reason, equation 11 is used to estimate **us**.
Once **Ms** is calculated, the procedure follows the same steps as in the previous case. The app will estimate the free flow and the shocked and reflected conditions based on the initial conditions provided. The Pitot pressure **pp** will be estimated from the velocity **us** using the corresponding relations [@srini87]. Finally, the app will estimate the shock wave velocity **us**.

The minimum data required to use the 'Analytical Ms' are: the driver composition, where you can choose between the gases He, air, N2, H2, and Ar; the pressure and temperature of the driver gas, which must be entered in megapascals and kelvin units; finally, similar to the 'Experimental Data' tab, the initial condition of the test gas.

The [GO!] button should be pressed again to execute the calculation and view the output log.


# RESULTS AND VALIDATION

The results are presented in three stages: first, in section 2.1, parameters calculated by the app for the shock and reflected conditions are compared with similar results obtained through programs available online; second, in section 2.2, a comparison is made between experimental and calculated values for the Pitot pressure of the free stream; finally, the effects of the g factor are presented for two distinct tunnels.

## Shock Tube

The calculations for the shock tube were compared to results obtained through the programs GasEq[@morley07], CeaRun[@gordon1994], and WiSTL[@wisl2025]. While the first two are programs used to calculate thermodynamic properties for chemical equilibrium of various mixtures, the last one corresponds to a calculator developed for studying shock tubes, assuming the simplification of a calorically perfect gas.
The comparisons are made by fixing the pressure (p1) at 101 kPa and temperature (T1) at 298 K in the low-pressure section, while varying the shock Mach number from 2 to 15. Tables 1 and 2 summarize, for the shock and reflected conditions respectively, the pressure and temperature data obtained through the app and their corresponding values from other programs.
It is noted that the largest discrepancies occur when comparing results from WiSTL, a program whose approach corresponds to a limiting theoretical case, characterized by neglecting phenomena such as dissociation and activation of internal energy modes.
The 'Deviation %' column, constructed to illustrate the maximum relative deviation between parameters calculated by the app and the chemical equilibrium programs, shows deviations lower than 3% for pressure and 2% for temperature data, respectively.

Table 1. Comparison between the app and other similar programs; initial conditions: p1 = 101 kPa, T1 = 298 K. The data corresponds to the calculated pressures for shock and reflected conditions.

| Input                 |                      | Shocked:              |                      |                       |                    | Reflected:             |                       |                      |                       |                    |
| --------------------- | -------------------- | --------------------- | -------------------- | --------------------- | ------------------ | ---------------------- | --------------------- | -------------------- | --------------------- | ------------------ |
| Ms                    | us (m/s)             | WiSTL                 | Gaseq                | CeaRun                | App                | WiSTL                  | Gaseq                 | CeaRun               | App                   | Δ%                |
| --------------------- | -------------------- | --------------------- | -------------------- | --------------------- | ------------------ | ---------------------- | --------------------- | -------------------- | --------------------- | ------------------ |
| 2                     | 692                  | 0.46                  | 0.46                 | 0.46                  | 0.47               | 2.0                    | 1.52                  | 1.52                 | 1.52                  | 1.56               |
| 5                     | 1730                 | 2.94                  | 3.02                 | 3.01                  | 3.06               | 1.6                    | 19.36                 | 21.00                | 20.99                 | 21.32              |
| 7                     | 2422                 | 5.78                  | 6.02                 | 6.01                  | 6.11               | 1.7                    | 41.63                 | 49.07                | 49.00                 | 49.72              |
| 10                    | 3461                 | 11.8                  | 12.6                 | 12.6                  | 12.8               | 1.8                    | 89.51                 | 127.0                | 126.4                 | 129.5              |
| 15                    | 5191                 | 26.6                  | 28.7                 | 28.7                  | 29.3               | 1.7                    | 207.3                 | 331.9                | 328.5                 | 336.4              |

Table 2. Comparison between the app and other similar programs; initial conditions: p1 = 101 kPa, T1 = 298 K. The data corresponds to the calculated temperatures for shock and reflected conditions.



| Temperature Comparison  |                    | Shock Condition:      |                      |                       |                    | Reflected Condition:   |                       |                      |                       |                    |
| ----------------------- | ------------------ | --------------------- | -------------------- | --------------------- | ------------------ | ---------------------- | --------------------- | -------------------- | --------------------- | ------------------ |
| Ms                      | us (m/s)           | WiSTL                 | Gaseq                | CeaRun                | App                | WiSTL                  | Gaseq                 | CeaRun               | App                   | Δ%                |
| ----------------------- | ------------------ | --------------------- | -------------------- | --------------------- | ------------------ | ---------------------- | --------------------- | -------------------- | --------------------- | ------------------ |
| 2                       | 692                | 504                   | 501                  | 501                   | 501                | 747                    | 730                   | 729                  | 732                   | 0.36               |
| 5                       | 1730               | 1735                  | 1588                 | 1587                  | 1601               | 3557                   | 2842                  | 2842                 | 2875                  | 1.15               |
| 7                       | 2422               | 3133                  | 2642                 | 2642                  | 2664               | 6753                   | 4553                  | 4548                 | 4525                  | 0.51               |
| 10                      | 3461               | 6103                  | 4275                 | 4272                  | 4252               | 13544                  | 7686                  | 7546                 | 7567                  | 0.28               |
| 15                      | 5191               | 13380                 | 7330                 | 7272                  | 7258               | 30186                  | 12057                 | 11694                | 11653                 | 0.35               |


# References
