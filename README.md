

# The **STT Calculator**

The  **STT Calculator**  is a mobile application designed to facilitate the rapid characterization of **shock tube** and **tunnel experiments**. It enables the estimation of post-shock gas conditions while considering real-air thermodynamic equilibrium effects.

The app calculates parameters such as pressure, temperature, and flow velocity based on initial data, including the test gas pressure and temperature, as well as the incident shock wave velocity. Additionally, it can estimate free-stream conditions at the nozzle exit, assuming an isentropic process.

With a simple and intuitive interface, the **STT Calculator** streamlines the analysis and planning of aerodynamic tests, allowing researchers, engineers, and students to quickly obtain experimental diagnostics without the need for complex software or extensive manual calculations.

# The Code

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

All the equations of state used in the app are based on the work of , in which curve fitting was defined to calculate the thermodynamic properties of real air in equilibrium at temperatures up to 20,000 K. These curve fits can be found transcribed in Java in [3], the code used and adapted in the development of the app.

### Reflected Condition

The reflected condition is calculated in a manner similar to the shocked condition; however, it differs in boundary condition: the reflected shock wave must have such magnitude that it brings the test gas to rest under the shocked condition, i.e., **u5 = 0**. Although this is a typical condition for shock tubes, this consideration is particularly valid in shock tunnels where the area restriction caused by the nozzle exceeds about 90% of the area of the cross-section and end of the tube. Thus, the conservation equations used to calculate the reflected condition are [10]:

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

The reservoir condition is included in the app to cover experiments performed under the under-tailored/over-tailored conditions, cases where successive expansion/compression waves, resulting from the interaction between the reflected shock wave and the contact surface, reach the test gas after the establishment of the reflected condition. While they cause entropy increases, the shock waves that occur in the over-tailored case can be approximated as isentropic processes [2,14], simplifying the estimation of the reservoir condition. Expansion waves, resulting from the under-tailored case, are naturally isentropic.

Thus, knowing the specific entropy **s5** of the reflected condition and providing the reservoir pressure **p0**, the reservoir conditions are calculated using the equations of state from [1]. In the absence of a measurement for the reservoir pressure, the app assumes its value is equal to that calculated for the reflected condition: **p0 = p5**.

## Nozzle-Exit Flow

The free flow will be calculated and displayed if at least one of the following parameters is entered by the user: the nozzle area ratio, represented by **A/A***, or an experimental measurement of the Pitot pressure, denoted as **pt**. To do this, it is assumed that the flow evolves from the reservoir condition to the nozzle exit in an adiabatic and non-reactive manner.

In the first case, when the area ratio **A/A*** is known, the Mach number of the free flow **Me** is found using the following equation [11]:

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

# Results and validation

The results are presented in three stages: first, in section 2.1, parameters calculated by the app for the shock and reflected conditions are compared with similar results obtained through programs available online; second, in section 2.2, a comparison is made between experimental and calculated values for the Pitot pressure of the free stream; finally, the effects of the g factor are presented for two distinct tunnels.

## Shock tube results

The calculations for the shock tube were compared to results obtained through the programs GasEq[7], CeaRun[6,8], and WiSTL[9]. While the first two are programs used to calculate thermodynamic properties for chemical equilibrium of various mixtures, the last one corresponds to a calculator developed for studying shock tubes, assuming the simplification of a calorically perfect gas.

The comparisons are made by fixing the pressure (p1) at 101 kPa and temperature (T1) at 298 K in the low-pressure section, while varying the shock Mach number from 2 to 15. Tables 1 and 2 summarize, for the shock and reflected conditions respectively, the pressure and temperature data obtained through the app and their corresponding values from other programs.

It is noted that the largest discrepancies occur when comparing results from WiSTL, a program whose approach corresponds to a limiting theoretical case, characterized by neglecting phenomena such as dissociation and activation of internal energy modes.
The 'Deviation %' column, constructed to illustrate the maximum relative deviation between parameters calculated by the app and the chemical equilibrium programs, shows deviations lower than 3% for pressure and 2% for temperature data, respectively.

Table 1. Comparison between the app and other similar programs; initial conditions: p1 = 101 kPa, T1 = 298 K. The data corresponds to the calculated pressures for shock and reflected conditions.

| Pressure Comparison |                    | Shocked Condition: |                 |                  |               | Reflected Condition: |                 |                  |               |               |
| ------------------- | ------------------ | ------------------ | --------------- | ---------------- | ------------- | -------------------- | --------------- | ---------------- | ------------- | ------------- |
| **Ms**        | **us (m/s)** | **WiSTL**    | **Gaseq** | **CeaRun** | **App** | **WiSTL**      | **Gaseq** | **CeaRun** | **App** | **Δ%** |
| 2                   | 692                | 0.46               | 0.46            | 0.46             | 0.47          | 2.0                  | 1.52            | 1.52             | 1.52          | 1.56          |
| 5                   | 1730               | 2.94               | 3.02            | 3.01             | 3.06          | 1.6                  | 19.36           | 21.00            | 20.99         | 21.32         |
| 7                   | 2422               | 5.78               | 6.02            | 6.01             | 6.11          | 1.7                  | 41.63           | 49.07            | 49.00         | 49.72         |
| 10                  | 3461               | 11.8               | 12.6            | 12.6             | 12.8          | 1.8                  | 89.51           | 127.0            | 126.4         | 129.5         |
| 15                  | 5191               | 26.6               | 28.7            | 28.7             | 29.3          | 1.7                  | 207.3           | 331.9            | 328.5         | 336.4         |

Table 2. Comparison between the app and other similar programs; initial conditions: p1 = 101 kPa, T1 = 298 K. The data corresponds to the calculated temperatures for shock and reflected conditions.

| Input        |                    | Shocked:        |                 |                  |               | Reflected:      |                 |                  |               |               |
| ------------ | ------------------ | --------------- | --------------- | ---------------- | ------------- | --------------- | --------------- | ---------------- | ------------- | ------------- |
| **Ms** | **us (m/s)** | **WiSTL** | **Gaseq** | **CeaRun** | **App** | **WiSTL** | **Gaseq** | **CeaRun** | **App** | **Δ%** |
| 2            | 692                | 504             | 501             | 501              | 501           | 747             | 730             | 729              | 732           | 0.36          |
| 5            | 1730               | 1735            | 1588            | 1587             | 1601          | 3557            | 2842            | 2842             | 2875          | 1.15          |
| 7            | 2422               | 3133            | 2642            | 2642             | 2664          | 6753            | 4553            | 4548             | 4525          | 0.51          |
| 10           | 3461               | 6103            | 4275            | 4272             | 4252          | 13544           | 7686            | 7546             | 7567          | 0.28          |
| 15           | 5191               | 13380           | 7330            | 7272             | 7258          | 30186           | 12057           | 11694            | 11653         | 0.35          |

## Free stream results

The procedure adopted for estimating the free stream is validated by comparing it with experimental data for the Pitot pressure obtained through shock tunnel T2 [13]. For this, the initial temperature of the test gas was fixed at 298 K, and the area ratio of the nozzle was set to 220.57. The input parameters that varied were: initial pressure (p1), incident shock wave speed (us), and reservoir pressure (p0).

Tables 3 and 4 summarize the comparison between measured and calculated Pitot pressures, distinguishing by the composition of the Driver gas: Table 3 was constructed from experiments using helium as the Driver gas at 20.7 MPa, while Table 4 was based on experiments with air at the same pressure of 20.7 MPa. The deviation shown corresponds to the absolute deviation calculated between the experimental and calculated Pitot values. For comparison purposes, the tables also display the equivalent area ratios estimated from the experimental Pitot pressure recorded.

Table 3. Experimental Pitot Pressure (pt, Experimental Output) and Calculated Pitot Pressure (pt, APP-Calculated) and Equivalent Area Ratio Calculated (eq A/A*, APP-Calculated); Driver Gas: Helium.

| Input    |          |      |          | Experimental Output | APP - Calculated |         | Deviation |
| -------- | -------- | ---- | -------- | ------------------- | ---------------- | ------- | --------- |
| p1 (kPa) | us (m/s) | Ms   | p0 (MPa) | pt (kPa)            | pt (kPa)         | eq A/A* | (%)       |
| 6.7      | 2049     | 5.92 | 12.5     | 112                 | 103              | 204     | 8.3       |
| 26.7     | 1706     | 4.93 | 12.5     | 105                 | 100              | 209     | 5.1       |
| 96       | 1427     | 4.12 | 13.7     | 112                 | 106              | 208     | 5.6       |
| 288      | 1154     | 3.34 | 19.5     | 156                 | 147              | 208     | 5.8       |

Table 4. Experimental Pitot Pressure (pt, Experimental Output) and Calculated Pitot Pressure (pt, APP-Calculated) and Equivalent Area Ratio Calculated (eq A/A*, APP-Calculated); Driver Gas: Air.

| Input    |          |      |          | Experimental Output | APP - Calculated |         | Deviation     |
| -------- | -------- | ---- | -------- | ------------------- | ---------------- | ------- | ------------- |
| p1 (kPa) | us (m/s) | Ms   | p0 (MPa) | pt (kPa)            | pt (kPa)         | eq A/A* | Deviation (%) |
| 6.7      | 1050     | 3.04 | 3.5      | 34                  | 27               | 176     | 25.4          |
| 26.7     | 931      | 2.69 | 6.1      | 58                  | 46               | 175     | 26.5          |
| 96       | 846      | 2.44 | 3.1      | 29                  | 23               | 175     | 26.9          |
| 288      | 737      | 2.13 | 5.4      | 50                  | 40               | 174     | 26.5          |

The results calculated by the app show that when using helium as the Driver gas, which induces the test gas to higher stagnation pressures, the Pitot pressures showed deviations of at most 8.3%, lower than the 26% relative deviation observed when using air as the Driver gas. In absolute terms, the difference in pressures ranged from 5 to 12 kPa in both cases.

## The g-factor results

In order to calculate the g factor, experimental data from tunnels T1 and T2 were used to construct the graphs in Figures 1 and 2, which relate the incident Mach number s with the initial Driven pressure p1, while keeping the Driver pressure and ambient temperature constant

<img src="Images/T1_Validacao.png" alt="T1 ShockTunnel: Ms versus experimental p1 (points) and respective simulated curves for three g factors of 0.45, 0.55, and 0.65." height="250" />
Figure 1: T1 ShockTunnel: Ms versus experimental p1 (points) and respective simulated curves for three g factors of 0.45, 0.55, and 0.65.

<img src="Images/T2_Validacao.png" alt="T1 ShockTunnel: Ms versus experimental p1 (points) and respective simulated curves for three g factors of 0.45, 0.55, and 0.65." height="250" />
Figure 1: T2 Shock Tunnel: Ms versus experimental p1 (points) and three simulated curves for three g factors for both air and helium as the Driver gas.

For the results in Figure 1, corresponding to shock tunnel T1, Helium was used as the Driver gas at a pressure of 6 MPa. The tunnel is characterized by a unit area ratio between the Driver and Driven sections. According to the figure, the Ms x p1 relation can be explained by assuming a g factor of approximately 0.55 in the operational range between 8 and 130 kPa. It is noteworthy that an ideal g factor increases as the pressure p1 increases.

For  the results in Figure 2, corresponding to shock tunnel T2, both Helium and air were used as the Driver gas, at a pressure of 20.7 MPa in both cases. The tunnel has an area ratio of 2.25 between the Driver and Driven sections. According to the figure, the Ms x p1 relation when using air can be explained by assuming a g factor of approximately 0.9 in the operational range between 7 and 290 kPa. When using Helium, an average g factor of 1.05 is identified. It is noted that due to the area ratio used, the tunnel has a g factor of approximately 1, regardless of the Driver gas. Again, it is observed that an ideal g factor increases as the pressure p1 increases.

Thus, the T1 and T2 tunnels, with different characteristics, show that the g factor can be assumed constant for a wide operational range of p4/p1, and can be estimated from a reduced number of experiments. Therefore, from the obtained g factor, the application can be used for experiment characterization based on purely analytical considerations combined with the correction g factor.

# References

- **[1] Srinivasan, S., Tanehill, J. C., & Weilmuenster, K. J.** (1987). *Simplified curve fits for the thermodynamic properties of equilibrium air*. NASA Langley Research Center. Relatório NASA-RP-1181.
- **[2] Copper, J. A.** (1961). *An experimental investigation of the equilibrium interface technique*. Tese de doutorado, California Institute of Technology.
- **[3] Java code for the thermodynamics properties of equilibrium air**. Disponível em: [https://devenport.aoe.vt.edu/tgas/](https://devenport.aoe.vt.edu/tgas/) (Acesso em: 13 fev. 2025).
- **[4] Minucci, M., & Nagamatsu, H.** (1991). *An investigation of hypersonic shock tunnel testing at an equilibrium interface condition of 4100 K - Theory and experiment*. 22nd Fluid Dynamics, Plasma Dynamics and Lasers Conference, p. 1707. [doi:10.2514/6.1991-1707](https://doi.org/10.2514/6.1991-1707).
- **[5] Resler, E. L., Lin, S.-C., & Kantrowitz, A.** (1952). *The production of high temperature gases in shock tubes*. Journal of Applied Physics, **23**(12), 1390–1399. [doi:10.1063/1.1702080](https://doi.org/10.1063/1.1702080).
- **[6] Gordon, S., & McBride, B. J.** (1994). *Computer program for calculation of complex chemical equilibrium compositions and applications. Part 1: Analysis*.
- **[7] Morley, C.** (2007). *GasEQ: A Chemical Equilibrium Program for Windows*. Disponível em: [http://www.gaseq.co.uk/](http://www.gaseq.co.uk/) (Acesso em: 13 fev. 2025).
- **[8] McBride, B. J.** (1992). *Computer program for calculating and fitting thermodynamic functions*. NASA Technical Report 1271. National Aeronautics and Space Administration.
- **[9] Wisconsin Shock Tube Laboratory**. *WIStL Gas Dynamic Calculator*. Disponível em: [https://silver.neep.wisc.edu/~shock/tools/gdcalc.html](https://silver.neep.wisc.edu/~shock/tools/gdcalc.html) (Acesso em: 13 fev. 2025).
- **[10] Minucci, M. A. S.** (1991). *An experimental investigation of a 2-D scramjet inlet at flow Mach numbers of 8 to 25 and stagnation temperatures of 800 to 4,100 K*. Tese de doutorado, Rensselaer Polytechnic Institute.
- **[11] Whitside, R. W., Chan, W. Y. K., Smart, M. K., & Morgan, R. G.** (2021). *The effect of increased throat size on the nozzle-supply flow in reflected shock tunnels*. Shock Waves, **31**, 419–426. [doi:10.1007/s00193-021-01017-7](https://doi.org/10.1007/s00193-021-01017-7).
- **[12] Anderson, J. D.** (2021). *Modern compressible flow: with historical perspective*. McGraw-Hill Education, Boston.
- **[13] Pereira, F. R. T., Rosa, M. A. P., & Rocamora Junior, F. D.** (2009). *STCALC_T2 – Simulador do Túnel de Choque Hipersônico T2*. III Seminário de Iniciação Científica Institucional do IEAv, São José dos Campos, SP.
- **[14] Minucci, M. A. S., & Nagamatsu, H. T.** (1993). *Hypersonic shock-tunnel testing at an equilibrium interface condition of 4100 K*. Journal of Thermophysics and Heat Transfer, **7**(2), 251–260. [doi:10.2514/3.414](https://doi.org/10.2514/3.414).
