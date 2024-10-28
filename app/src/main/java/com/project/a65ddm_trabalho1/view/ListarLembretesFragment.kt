package com.project.a65ddm_trabalho1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.a65ddm_trabalho1.R
import com.project.a65ddm_trabalho1.model.Lembrete
import com.project.a65ddm_trabalho1.model.LembreteAdapter
import com.project.a65ddm_trabalho1.viewModel.LembreteViewModel

class ListarLembretesFragment : Fragment() {

    private lateinit var viewModel: LembreteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LembreteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listar_lembretes, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_lembretes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this).get(LembreteViewModel::class.java)

        // Observar os lembretes e configurar o adaptador
        viewModel.lembretes.observe(viewLifecycleOwner) { lembretes ->
            if(lembretes != null) {
                adapter = LembreteAdapter(lembretes,
                    onEditClick = { lembrete ->
                        // Implementar a ação de editar
                        Toast.makeText(requireContext(), "Editar ${lembrete.dataLembrete}", Toast.LENGTH_SHORT).show()
                    },
                    onDeleteClick = { lembrete ->
                        viewModel.deletarLembrete(lembrete)
                        Toast.makeText(requireContext(), "Lembrete deletado!", Toast.LENGTH_SHORT).show()
                    },
                    onSwitchChange = { lembrete, ativado ->
                        // Atualizar o status do lembrete (ativado/desativado)
//                        viewModel.ativarDesativarLembrete(lembrete, ativado)
                        Toast.makeText(requireContext(), "Tentado mudar switch", Toast.LENGTH_SHORT).show()
                    }
                )
                recyclerView.adapter = adapter
            }
        }

        return view
    }
}
