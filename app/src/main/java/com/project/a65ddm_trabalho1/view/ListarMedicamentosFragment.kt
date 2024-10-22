package com.project.a65ddm_trabalho1.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

//        // Observar os medicamentos e atualizar a lista
//        viewModel.medicamentos.observe(viewLifecycleOwner) { medicamentos ->
//            Log.d("TAG", "Medicamentos na lista: $medicamentos")
//            adapter = MedicamentoAdapter(medicamentos,
//                onEditClick = { medicamento ->
//                    // Implementar ação de edição aqui
//                    Toast.makeText(requireContext(), "Editar ${medicamento.nome}", Toast.LENGTH_SHORT).show()
//                },
//                onDeleteClick = { medicamento ->
//                    // Implementar ação de deleção aqui
//                    viewModel.deletarMedicamento(medicamento)
//                    Toast.makeText(requireContext(), "Deletar ${medicamento.nome}", Toast.LENGTH_SHORT).show()
//                })
//            recyclerView.adapter = adapter
//        }

        viewModel.medicamentos.observe(viewLifecycleOwner) { medicamentos ->
            if (medicamentos != null) {
                adapter = MedicamentoAdapter(medicamentos, onEditClick = {}, onDeleteClick = {})
                recyclerView.adapter = adapter
            }
        }

        return view
    }
}
