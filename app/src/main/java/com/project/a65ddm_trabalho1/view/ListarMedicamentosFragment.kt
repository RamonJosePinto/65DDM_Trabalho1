package com.project.a65ddm_trabalho1.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.a65ddm_trabalho1.R
import com.project.a65ddm_trabalho1.model.MedicamentoAdapter
import com.project.a65ddm_trabalho1.viewModel.MedicamentoViewModel

class ListarMedicamentosFragment : Fragment() {

    private lateinit var viewModel: MedicamentoViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MedicamentoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listar_medicamentos, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_medicamentos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)


        val navController = findNavController()
        viewModel.medicamentos.observe(viewLifecycleOwner) { medicamentos ->
            if (medicamentos != null) {
                adapter = MedicamentoAdapter(medicamentos, onEditClick = {},
                    onDeleteClick = { medicamento ->
                        AlertDialog.Builder(requireContext())
                            .setTitle("Deletar Medicamento")
                            .setMessage("Você tem certeza que deseja deletar este medicamento?")
                            .setPositiveButton("Sim") { _, _ ->
                                viewModel.deletarMedicamento(medicamento)
                            }
                            .setNegativeButton("Não", null)
                            .show()
                    },
                    onDetailClick = {medicamento ->
                        val action = ListarMedicamentosFragmentDirections.actionListarMedicamentosFragmentToDetalhesMedicamentoFragment(medicamento)
                        navController.navigate(action)
                })
                recyclerView.adapter = adapter
            }
        }

        return view
    }
}
