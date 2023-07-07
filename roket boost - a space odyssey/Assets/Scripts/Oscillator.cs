using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;

public class Oscillator : MonoBehaviour
{
    Vector3 startingPosition;
    [SerializeField] Vector3 movementVector;
    [SerializeField] [Range(0,1)] float movementFactor;
    [SerializeField] float period = 2f;

    // Start is called before the first frame update
    void Start()
    {
        startingPosition = transform.position;
        
    }

    // Update is called once per frame
    void Update()
    {   if (period <= Mathf.Epsilon) {return;} // to prevent zerodivision error
        float cycles = Time.time / period;
        const float tau = Mathf.PI * 2; // tau is double of pi basically (6.283)
        float rawSinWawe = Mathf.Sin(cycles * tau); // going form -1 to 1

        movementFactor = (rawSinWawe + 1f) / 2f; // 0 to 1

        Vector3 offset = movementVector * movementFactor;
        transform.position = startingPosition + offset;
    }
}
